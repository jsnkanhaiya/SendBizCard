package com.sendbizcard.ui.ourServices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentOurServicesBinding
import com.sendbizcard.dialog.AddServicesDialog
import com.sendbizcard.models.request.addCard.ServicesItem
import com.sendbizcard.utils.UserSessionManager
import com.sendbizcard.utils.gone
import com.sendbizcard.utils.visible
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

        initViews()
        initOnClicks()
        setUpAdapter()
    }

    private fun initViews() {
        binding.tvToolBar.tvBack.visible()
        binding.tvToolBar.tvTitle.gone()
        binding.tvToolBar.tvBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initOnClicks() {
        binding.btnAdd.setOnClickListener {
            val dialog = AddServicesDialog.newInstance()
            dialog.onSaveButtonClick = object : AddServicesDialog.OnSaveButtonClick {
                override fun addServicesItem(serviceEntered: String) {
                    ourServicesAdapter.add(ServicesItem(name = serviceEntered))
                    ourServicesAdapter.notifyDataSetChanged()
                }
            }
            dialog.show(parentFragmentManager,"add_services_dialog")
        }

        binding.btnSave.setOnClickListener {
            val ourServicesList = ourServicesAdapter.list
            UserSessionManager.addDataInServiceList(ourServicesList)
        }
    }

    private fun setUpAdapter() {
        val ourServicesList = UserSessionManager.getDatFromServiceList()
        ourServicesAdapter.addAll(ourServicesList)
        binding.rvOurServices.adapter = ourServicesAdapter
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOurServicesBinding
        get() = FragmentOurServicesBinding::inflate
}