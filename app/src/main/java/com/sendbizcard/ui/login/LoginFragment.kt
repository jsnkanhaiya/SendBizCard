package com.sendbizcard.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val loginViewModel: LoginViewModel by viewModels()
    private var binding: FragmentLoginBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initViews()
        setupObservers()
    }

    private fun setupObservers() {
        loginViewModel.loginReponse.observe(viewLifecycleOwner, Observer {
            var i = Intent(requireContext(),HomeActivity::class.java)
            requireActivity().startActivity(i)
        })
    }

    private fun initViews() {
        binding?.btnSave?.setOnClickListener {
            val emailId = binding?.etEmailID?.text.toString()
            val password = binding?.etPassword?.text.toString()
            if (loginViewModel.isValidLoginData(emailId,password)) {
                loginViewModel.login(emailId,password)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

}