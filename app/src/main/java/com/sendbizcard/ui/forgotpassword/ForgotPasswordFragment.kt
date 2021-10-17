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


    private val TAG = "CreateNewPasswordFragment"

      val forgotPasswordViewmodel: ForgotPasswordViewModel by viewModels()
    private var _binding: FragmentForgotPasswordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = getViewBinding()
        initViews()
        setupObservers()
        initSpanUI()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initSpanUI() {
        binding?.tvAlreadyAccount?.movementMethod =
            LinkMovementMethod.getInstance() // without LinkMovementMethod, click will not work
        binding?.tvAlreadyAccount?.text = span {
            +resources.getString(R.string.alreadyAccount)
            span {
                text = " "+ resources.getString(R.string.signin)
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
        forgotPasswordViewmodel.forgotPasswordReponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarContainer.visibility = View.GONE

            findNavController().navigate(
                R.id.nav_verifyForgotOtp,
                bundleOf("otp" to it.otp.toString()),
               getDefaultNavigationAnimation()
            )
        })
        forgotPasswordViewmodel.showNetworkError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it,requireActivity(), it1) }
        })

        forgotPasswordViewmodel.showUnknownError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        })

        forgotPasswordViewmodel.showServerError.observe(this ) { errorMessage ->
            binding.progressBarContainer.visibility = View.GONE
            Log.d("Login Error",errorMessage)
        }

    }

    private fun initViews() {
        binding.progressBarContainer.visibility = View.GONE
        binding.btnSave.setOnClickListener {
            val emailId = binding?.etEmailID?.text.toString()
            if (forgotPasswordViewmodel.isValidData(emailId)) {
                binding.progressBarContainer.visibility = View.VISIBLE
                forgotPasswordViewmodel.forgotPasswordUser(emailId)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForgotPasswordBinding
        get() = FragmentForgotPasswordBinding::inflate
}

