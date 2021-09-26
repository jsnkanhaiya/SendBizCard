package com.sendbizcard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentHomeBinding
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.ui.home.adapter.SavedCardsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var savedCardsAdapter: SavedCardsAdapter

    private val homeViewModel: HomeViewModel by viewModels()

    private var binding: FragmentHomeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        setUpObservers()
        homeViewModel.getListOfSavedCards()
    }

    private fun setUpObservers() {
        homeViewModel.listOfSavedCards.observe(viewLifecycleOwner, Observer { listOfSavedCards ->
            setUpAdapterWithUI(listOfSavedCards)
        })
    }

    private fun setUpAdapterWithUI(listOfSavedCards: ArrayList<SavedCards>) {
        savedCardsAdapter.addAll(listOfSavedCards)
        binding?.rvSavedCards?.adapter = savedCardsAdapter
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

}