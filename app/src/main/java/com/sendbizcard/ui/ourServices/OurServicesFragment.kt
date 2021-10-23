package com.sendbizcard.ui.ourServices

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentOurServicesBinding
import com.sendbizcard.dialog.AddServicesDialog
import com.sendbizcard.models.request.addCard.ServicesItem
import com.sendbizcard.utils.AlertDialogWithImageView
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initOnClicks()
        setUpAdapter()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun initOnClicks() {
        binding.btnAdd.setOnClickListener {
            val dialog = AddServicesDialog.newInstance()
            dialog.onSaveButtonClick = object : AddServicesDialog.OnSaveButtonClick {
                @SuppressLint("NotifyDataSetChanged")
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
            showSuccessDialog()
        }
    }

    private fun setUpAdapter() {
        val ourServicesList = UserSessionManager.getDataFromServiceList()
        ourServicesAdapter.addAll(ourServicesList)
        binding.rvOurServices.adapter = ourServicesAdapter
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showSuccessDialog() {
        AlertDialogWithImageView.showDialog(
            requireFragmentManager().beginTransaction(),
            requireContext(),
            requireContext().resources.getString(R.string.success_title),
            requireContext().resources.getString(R.string.saved_successfully),
            R.drawable.ic_success,
            onDismiss = {
                findNavController().popBackStack()
            }
        )


    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOurServicesBinding
        get() = FragmentOurServicesBinding::inflate
}