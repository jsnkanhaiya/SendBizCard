package com.sendbizcard.ui.cardlist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.base.BaseViewHolder
import com.sendbizcard.databinding.FragmentCardListBinding
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.models.response.CardDetailsItem
import com.sendbizcard.models.response.CardListResponseModel
import com.sendbizcard.ui.main.MainActivity
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
    var deletedId =""
    private val cardListViewModel: CardListViewModel by viewModels()
    private val viewCardViewModel: ViewCardViewModel by viewModels()
    private lateinit var binding: FragmentCardListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        observeData()
        initOnClicks()
        initOnTextChangedListener()
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
                    findNavController().navigate(
                        R.id.nav_view_card, bundleOf("id" to data.id),
                        getDefaultNavigationAnimation()
                    )
                }

                override fun onShareClicked(data: CardDetailsItem, pos: Int) {
                    Log.d("CardListFragment", "ShareClickedCallback")
                    viewCardViewModel.getCardURL(data.id.toString(), cardListViewModel.getThemeId())
                    // shareApp(requireContext(),data.)
                }

                override fun onDeleteClicked(data: CardDetailsItem, pos: Int) {
                    Log.d("CardListFragment", "DeleteClickedCallback")
                    deletedId = data.id.toString()
                    binding.progressBarContainer.visible()
                    viewCardViewModel.deleteCard(data.id.toString())

                }
            }
        binding.rvCardList.adapter = cardListAdapter
    }

    private fun setUpSearchDataInAdapter(cardList: List<CardDetailsItem>) {
        cardListAdapter.addAll(cardList)
        cardListAdapter.notifyDataSetChanged()
    }

    private fun showSuccessDialog() {
        val fragment = CommonDialogFragment.newInstance(resources.getString(R.string.success_title),
            resources.getString(R.string.success_title_card_delete),"",R.drawable.ic_icon_success)
        fragment.show(parentFragmentManager,"CardListFragment")
    }

    private fun observeData() {

        viewCardViewModel.deleteCardResponse.observe(this) {
            binding.progressBarContainer.gone()
            hideProgressBar()
            showSuccessDialog()
            var deletedCardIndex = -1
           for ((index, value) in cardListAdapter.list.withIndex()!!) {
                println("the element at $index is $value")
                if (value.id?.equals(deletedId) == true){
                    deletedCardIndex = index
                    break
                }
            }
            if (deletedCardIndex!=-1){
                cardListAdapter.list.removeAt(deletedCardIndex)
                cardListAdapter.notifyItemRemoved(deletedCardIndex)
            }
        }

        viewCardViewModel.viewCardResponse.observe(this) { cardList ->
            hideProgressBar()
            cardList.redirectUrl?.let { shareApp(requireContext(), it) }
        }

        cardListViewModel.cardListLiveData.observe(this) { cardList ->
            hideProgressBar()
            setUpAdapter(cardList)
        }

        cardListViewModel.cardSearchLiveData.observe(this) { searchCardList ->
            hideProgressBar()
            setUpAdapter(searchCardList)
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
        val fragment = CommonDialogFragment.newInstance(
            resources.getString(R.string.error),
            errorMessage, "", R.drawable.ic_icon_error
        )
        fragment.show(parentFragmentManager, "CardListFragment")
    }

    private fun initOnClicks() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.nav_home, null, getDefaultNavigationAnimation())
        }

        binding.imgSearch.setOnClickListener {
            val strSearch = binding.etSearch.text.toString()
            if (strSearch.isNotEmpty()) {
                showProgressBar()
                cardListViewModel.getCardSearchList(strSearch)
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBarContainer.visible()
    }

    private fun hideProgressBar() {
        binding.progressBarContainer.gone()
    }

    private fun initOnTextChangedListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                val searchData = binding.etSearch.text.toString()
                val cardList = cardListViewModel.cardListLiveData.value ?: ArrayList()
                if (searchData.isEmpty() && cardList.isNotEmpty()){
                    setUpSearchDataInAdapter(cardList)
                }
            }
        })


    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCardListBinding
        get() = FragmentCardListBinding::inflate
}