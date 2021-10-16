package com.sendbizcard.ui.ourServices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentOurServicesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OurServicesFragment : BaseFragment<FragmentOurServicesBinding>() {

    @Inject
    lateinit var ourServicesAdapter: OurServicesAdapter

    lateinit var binding: FragmentOurServicesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        setUpAdapter()

    }

    private fun setUpAdapter() {
        binding.rvOurServices.adapter = ourServicesAdapter
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOurServicesBinding
        get() = FragmentOurServicesBinding::inflate
}