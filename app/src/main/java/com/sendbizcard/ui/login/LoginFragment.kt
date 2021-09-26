package com.sendbizcard.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentLoginBinding
import com.sendbizcard.ui.register.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private lateinit var loginViewModel: LoginViewModel
    private var binding: FragmentLoginBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initViews()
        setupObservers()
    }

    private fun setupObservers() {

    }

    private fun initViews() {
        binding?.btnSave?.setOnClickListener {

        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

}