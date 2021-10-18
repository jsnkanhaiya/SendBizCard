package com.sendbizcard.ui.forgotpassword

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.*
import com.sendbizcard.utils.getDefaultNavigationAnimation
import com.sendbizcard.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import me.gujun.android.span.span

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>() {

    private val forgotPasswordViewModel: ForgotPasswordViewModel by viewModels()
    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initViews()
        setupObservers()
        initSpanUI()
    }

    private fun initSpanUI() {
        binding.tvAlreadyAccount.movementMethod =
            LinkMovementMethod.getInstance() // without LinkMovementMethod, click will not work
        binding.tvAlreadyAccount.text = span {
            +resources.getString(R.string.already_have_account)
            span {
                text = " "+ resources.getString(R.string.sign_in)
                textColor = ResourcesCompat.getColor(
                    requireContext().resources,
                    R.color.colorPrimary3,
                    null
                )
                onClick = {
                    //on click
                    findNavController().navigate(R.id.nav_login,null,
                        getDefaultNavigationAnimation()
                    )
                }
            }
        }
    }

    private fun setupObservers() {
        forgotPasswordViewModel.forgotPasswordReponse.observe(viewLifecycleOwner, {
            binding.progressBarContainer.visibility = View.GONE
            findNavController().navigate(
                R.id.nav_verifyForgotOtp,
                bundleOf("otp" to it.otp.toString()),
               getDefaultNavigationAnimation()
            )
        })
        forgotPasswordViewModel.showNetworkError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it,requireActivity(), it1) }
        })

        forgotPasswordViewModel.showUnknownError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        })

        forgotPasswordViewModel.showServerError.observe(this ) { errorMessage ->
            binding.progressBarContainer.visibility = View.GONE
            Log.d("Login Error",errorMessage)
        }

    }

    private fun initViews() {
        binding.progressBarContainer.visibility = View.GONE
        binding.btnSave.setOnClickListener {
            val emailId = binding.etEmailID.text.toString()
            if (forgotPasswordViewModel.isValidData(emailId)) {
                binding.progressBarContainer.visibility = View.VISIBLE
                forgotPasswordViewModel.forgotPasswordUser(emailId)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForgotPasswordBinding
        get() = FragmentForgotPasswordBinding::inflate
}

