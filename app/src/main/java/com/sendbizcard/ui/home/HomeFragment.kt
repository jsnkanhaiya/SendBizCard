package com.sendbizcard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sendbizcard.databinding.FragmentHomeBinding
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.ui.home.adapter.SavedCardsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {


    @Inject
    lateinit var savedCardsAdapter: SavedCardsAdapter

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        observeData()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getListOfSavedCards()
    }

    private fun observeData() {
        homeViewModel.listOfSavedCards.observe(viewLifecycleOwner, Observer { listOfSavedCards ->
            setUpAdapterWithUI(listOfSavedCards)
        })
    }

    private fun setUpAdapterWithUI(listOfSavedCards: ArrayList<SavedCards>) {
        savedCardsAdapter.addAll(listOfSavedCards)
        _binding?.rvSavedCards?.adapter = savedCardsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}