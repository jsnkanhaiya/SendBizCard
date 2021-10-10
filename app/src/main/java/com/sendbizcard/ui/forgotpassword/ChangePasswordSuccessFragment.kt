package com.sendbizcard.ui.forgotpassword

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentChangePasswordVerificationBinding
import com.sendbizcard.databinding.FragmentSuccssesfulMessageBinding
import com.sendbizcard.ui.otp.VerifyOtpViewModel
import com.sendbizcard.utils.AlertDialogWithImageView

class ChangePasswordSuccessFragment : BaseFragment<FragmentSuccssesfulMessageBinding>() {


    private val TAG = "VerifyOtpFragment"

    private val verifyOtpViewModel: VerifyOtpViewModel by viewModels()
    private var _binding: FragmentSuccssesfulMessageBinding? = null
    private  var otp= ""
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = getViewBinding()
        initViews()
    }


    private fun initViews() {
        binding.btnSave.setOnClickListener {
            findNavController().navigate(R.id.nav_login)
        }
    }




    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSuccssesfulMessageBinding
        get() = FragmentSuccssesfulMessageBinding::inflate
}