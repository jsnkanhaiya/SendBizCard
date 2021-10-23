package com.sendbizcard.ui.cardlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.base.BaseViewHolder
import com.sendbizcard.databinding.FragmentCardListBinding
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.models.response.CardDetailsItem
import com.sendbizcard.models.response.CardListResponseModel
import com.sendbizcard.utils.getDefaultNavigationAnimation
import com.sendbizcard.utils.gone
import com.sendbizcard.utils.visible
import com.sendbizcard.ui.sharecard.ViewCardViewModel
import com.sendbizcard.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CardListFragment : BaseFragment<FragmentCardListBinding>() {

    @Inject
    lateinit var cardListAdapter: CardListAdapter

    var cardList: CardListResponseModel? = null

    private val cardListViewModel: CardListViewModel by viewModels()
    private val viewCardViewModel: ViewCardViewModel by viewModels()
    private lateinit var binding: FragmentCardListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        observeData()
        initOnClicks()
        showProgressBar()
        cardListViewModel.getCardList()

    }

    private fun setUpAdapter(cardList: List<CardDetailsItem>) {
        cardListAdapter.addAll(cardList)
        cardListAdapter.cardClickListener =
            object : BaseViewHolder.ItemCardClickCallback<CardDetailsItem> {
                override fun onEditClicked(data: CardDetailsItem, pos: Int) {
                    Log.d("CardListFragment", "EditClickedCallback")
                    findNavController().navigate(R.id.nav_edit_card,null,
                        getDefaultNavigationAnimation())
                }

                override fun onPreviewClicked(data: CardDetailsItem, pos: Int) {
                    Log.d("CardListFragment", "PreviewClickedCallback")
                    findNavController().navigate(R.id.nav_view_card, bundleOf("id" to data.id),
                        getDefaultNavigationAnimation())
                }

                override fun onShareClicked(data: CardDetailsItem, pos: Int) {
                    Log.d("CardListFragment", "ShareClickedCallback")
                    viewCardViewModel.getCardURL(data.id.toString(),cardListViewModel.getThemeId())
                   // shareApp(requireContext(),data.)
                }

                override fun onDeleteClicked(data: CardDetailsItem, pos: Int) {
                    Log.d("CardListFragment", "DeleteClickedCallback")
                }
            }
        binding.rvCardList.adapter = cardListAdapter
    }


    private fun observeData() {

        viewCardViewModel.viewCardResponse.observe(this) { cardList ->
            hideProgressBar()
            cardList.redirectUrl?.let { shareApp(requireContext(), it) }
        }

        cardListViewModel.cardListLiveData.observe(this) { cardList ->
            hideProgressBar()
            setUpAdapter(cardList)
        }

        cardListViewModel.showNetworkError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        cardListViewModel.showUnknownError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        cardListViewModel.showServerError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }
    }

    private fun showErrorMessage(errorMessage: String) {
        hideProgressBar()
        val fragment = CommonDialogFragment.newInstance(resources.getString(R.string.error),
            errorMessage,"",R.drawable.ic_icon_error)
        fragment.show(parentFragmentManager,"CardListFragment")
    }

    private fun initOnClicks() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.nav_home, null, getDefaultNavigationAnimation())
        }
    }

    private fun showProgressBar() {
        binding.progressBarContainer.visible()
    }

    private fun hideProgressBar() {
        binding.progressBarContainer.gone()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCardListBinding
        get() = FragmentCardListBinding::inflate
}