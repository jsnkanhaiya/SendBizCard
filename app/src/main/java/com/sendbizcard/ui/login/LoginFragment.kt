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
import com.sendbizcard.dialog.ConfirmationDialogFragment
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

            val i = Intent(requireContext(), HomeActivity::class.java)
            requireActivity().startActivity(i)
        })

        loginViewModel.showNetworkError.observe(this, Observer {
            showlogoutDialog(it)
        })

        loginViewModel.showUnknownError.observe(this, Observer {
            showlogoutDialog(it)
        })


        loginViewModel.error.observe(viewLifecycleOwner, Observer {
            showlogoutDialog(it)
        })


    }

    private fun initViews() {
        binding?.btnSave?.setOnClickListener {
            val emailId = binding?.etEmailID?.text.toString()
            val password = binding?.etPassword?.text.toString()
            if (loginViewModel.isValidLoginData(emailId, password)) {
                loginViewModel.login(emailId, password)
            }
        }

        binding?.tvForgotPassword?.setOnClickListener {
            findNavController().navigate(R.id.nav_forgot_password)
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate


    private fun showlogoutDialog(message:String) {
        val confirmationDialogFragment = ConfirmationDialogFragment.Builder()
            .setAcceptButton(
                context?.resources?.getString(R.string.ok_confirmation)!!
            )
            .setTitle(
                context?.resources?.getString(R.string.error)!!
            )
            .setMessage(
                message
            )
            .setMessageTextColor(requireContext().resources.getColor(R.color.textcolour))
            .build()
        confirmationDialogFragment.show(
            requireActivity().supportFragmentManager,
            ConfirmationDialogFragment.TAG
        )

        confirmationDialogFragment.setButtonClickListener(object :
            ConfirmationDialogFragment.OnButtonClickListener {
            override fun onAcceptClick() {
                confirmationDialogFragment.dismiss()
            }

            override fun onRejectClick() {
            }
        })

    }

}