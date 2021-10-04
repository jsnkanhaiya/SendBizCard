package com.sendbizcard.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private lateinit var registerViewModel: RegisterViewModel
    private var binding: FragmentRegisterBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerViewModel =
            ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initViews()
        setUpObservers()
    }

    private fun setUpObservers() {

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