package com.sendbizcard.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import me.gujun.android.span.span

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initViews()
        initSpanUI()
        setupObservers()
    }

    private fun initSpanUI() {
        binding.tvAlreadyAccount.movementMethod =
            LinkMovementMethod.getInstance() // without LinkMovementMethod, click will not work
        binding.tvAlreadyAccount.text = span {
            +"Don't have an account? "
            span {
                text = "Create New Account"
                textColor = ResourcesCompat.getColor(
                    requireContext().resources,
                    R.color.colorPrimary3,
                    null
                )
                onClick = {
                    //on click
                }
            }
        }
    }

    private fun setupObservers() {
        loginViewModel.loginReponse.observe(viewLifecycleOwner, Observer {

            val i = Intent(requireContext(),HomeActivity::class.java)
            requireActivity().startActivity(i)
        })

        loginViewModel.showNetworkError.observe(this, Observer {

        })

        loginViewModel.showUnknownError.observe(this, Observer {

        })

        loginViewModel.showServerError.observe(this ) { errorMessage ->
            Log.d("Login Error",errorMessage)
        }


    }

    private fun initViews() {
        binding.btnSave.setOnClickListener {
            val emailId = binding.etEmailID.text.toString()
            val password = binding.etPassword.text.toString()
            if (loginViewModel.isValidLoginData(emailId,password)) {
                loginViewModel.login(emailId,password)
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.nav_forgot_password)
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

}