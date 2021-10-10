package com.sendbizcard.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private  val registerViewModel: RegisterViewModel by viewModels()
    private var binding: FragmentRegisterBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initViews()
        setUpObservers()
    }

    private fun setUpObservers() {
        registerViewModel.registerReponse.observe(viewLifecycleOwner, Observer {
                findNavController().navigate(R.id.nav_verifyOtp, bundleOf("otp" to it.data?.contactOtp))
        })
    }

    private fun initViews() {
        binding?.btnSave?.setOnClickListener {
            val name = binding?.etName?.text.toString()
            val emailId = binding?.etEmailID?.text.toString()
            val mobileNo = binding?.etMobile?.text.toString()
            val password = binding?.etPassword?.text.toString()
            val confPassword = binding?.etConfirmPassword?.text.toString()
            if (registerViewModel.isValidRegisterData(
                    name,
                    mobileNo,
                    emailId,
                    password,
                    confPassword
                )
            ) {
                registerViewModel.registerUser(name, mobileNo, emailId, password, confPassword)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        get() = FragmentRegisterBinding::inflate


}