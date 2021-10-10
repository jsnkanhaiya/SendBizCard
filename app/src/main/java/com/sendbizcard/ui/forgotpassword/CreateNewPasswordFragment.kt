package com.sendbizcard.ui.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentCreateNewPasswordBinding
import com.sendbizcard.models.response.BaseResponseModel

class CreateNewPasswordFragment : BaseFragment<FragmentCreateNewPasswordBinding>(){


    private val TAG = "CreateNewPasswordFragment"

    private  val forgotPasswordViewmodel: ForgotPasswordViewModel by viewModels()
    private var _binding: FragmentCreateNewPasswordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var otp =""



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = getViewBinding()
        initViews()
        setupObservers()
    }

    private fun setupObservers() {
        forgotPasswordViewmodel.changePasswordResponse.observe(viewLifecycleOwner, Observer {

        })
    }

    private fun initViews() {
        binding.btnSave.setOnClickListener {
            val password = binding.etNewPassword.text.toString()
            val confpassword = binding.etConfirmPassword.text.toString()
            if (forgotPasswordViewmodel.isValidChangePasswordData(otp,password,confpassword)){
                forgotPasswordViewmodel.changePasswordUser(otp,password,confpassword)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCreateNewPasswordBinding
        get() = FragmentCreateNewPasswordBinding::inflate
}