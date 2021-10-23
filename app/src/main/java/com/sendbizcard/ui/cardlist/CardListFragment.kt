package com.sendbizcard.ui.cardlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.base.BaseViewHolder
import com.sendbizcard.databinding.FragmentCardListBinding
import com.sendbizcard.databinding.FragmentLoginBinding
import com.sendbizcard.models.response.CardDetailsItem
import com.sendbizcard.models.response.CardListResponseModel
import com.sendbizcard.ui.ourServices.OurServicesAdapter
import com.sendbizcard.utils.UserSessionManager
import com.sendbizcard.utils.getDefaultNavigationAnimation
import com.sendbizcard.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CardListFragment : BaseFragment<FragmentCardListBinding>(){

    @Inject
    lateinit var cardListAdapter: CardListAdapter

    var cardList : CardListResponseModel? = null

    private val cardListViewModel: CardListViewModel by viewModels()
    private lateinit var binding: FragmentCardListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        binding.progressBarContainer.visibility = View.VISIBLE
        cardListViewModel.getCardList()
        initViews()
        initOnClicks()
        setupObservers()
       // setUpAdapter()
    }

   /* private fun setUpAdapter() {
        //val ourServicesList = UserSessionManager.getDatFromServiceList()
       // cardList?.data?.cardDetails.let {
            if (!cardList?.data?.cardDetails.isNullOrEmpty()){
                cardListAdapter.addAll(cardList?.data?.cardDetails as List<CardDetailsItem>)
                binding.rvCardList.adapter = cardListAdapter
            }
       // }
    }*/

    private fun initViews() {

    }

    private fun setupObservers() {
        cardListViewModel.cardListResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarContainer.visibility = View.GONE
            cardListAdapter.addAll(it.data?.cardDetails as List<CardDetailsItem>)
            cardListAdapter.cardClickListener = object : BaseViewHolder.ItemCardClickCallback<CardDetailsItem> {
                override fun onEditClicked(data: CardDetailsItem, pos: Int) {
                    Log.d("CardListFragment","EditClickedCallback")
                }

                override fun onPreviewClicked(data: CardDetailsItem, pos: Int) {
                    Log.d("CardListFragment","PreviewClickedCallback")
                }

                override fun onShareClicked(data: CardDetailsItem, pos: Int) {
                    Log.d("CardListFragment","ShareClickedCallback")
                }

                override fun onDeleteClicked(data: CardDetailsItem, pos: Int) {
                    Log.d("CardListFragment","DeleteClickedCallback")
                }
            }
            binding.rvCardList.adapter = cardListAdapter
        })

        cardListViewModel.showNetworkError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it,requireActivity(), it1) }
        })

        cardListViewModel.showUnknownError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        })

        cardListViewModel.showServerError.observe(this ) { errorMessage ->
            binding.progressBarContainer.visibility = View.GONE
            Log.d("Login Error",errorMessage)
        }
    }

    private fun initOnClicks() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.nav_home,null, getDefaultNavigationAnimation())
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCardListBinding
        get() = FragmentCardListBinding::inflate
}