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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordSuccessFragment : BaseFragment<FragmentSuccssesfulMessageBinding>() {

    private val verifyOtpViewModel: VerifyOtpViewModel by viewModels()
    private lateinit var binding: FragmentSuccssesfulMessageBinding
    private  var otp= ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
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