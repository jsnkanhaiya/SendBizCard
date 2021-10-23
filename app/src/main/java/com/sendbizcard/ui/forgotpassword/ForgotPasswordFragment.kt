package com.sendbizcard.ui.forgotpassword

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentForgotPasswordBinding
import com.sendbizcard.utils.FieldEnum
import com.sendbizcard.utils.checkValidations
import com.sendbizcard.utils.getDefaultNavigationAnimation
import com.sendbizcard.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import me.gujun.android.span.span

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>() {

    private val forgotPasswordViewModel: ForgotPasswordViewModel by viewModels()
    private lateinit var binding: FragmentForgotPasswordBinding


    private var isChangePassword = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        val bundle = this.arguments
        if (bundle != null) {
            isChangePassword = bundle.getBoolean("isChangepassword")
            //binding.otpPinView.text=
            //Toast.makeText(context, "isChangePassword is " + isChangePassword, Toast.LENGTH_LONG).show()
        }

        if (isChangePassword) {
            binding.tvTitle.text = resources.getString(R.string.Change_Password)
            binding.tvTitleImage.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_change_password))
        }
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
                text = " " + resources.getString(R.string.sign_in)
                textColor = ResourcesCompat.getColor(
                    requireContext().resources,
                    R.color.colorPrimary3,
                    null
                )
                onClick = {
                    //on click
                    findNavController().navigate(
                        R.id.nav_login, null,
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
                bundleOf("otp" to it.otp.toString(),"emailID" to binding.etEmailID.text.toString(),"isChangepassword" to isChangePassword),
                getDefaultNavigationAnimation()
            )
        })
        forgotPasswordViewModel.showNetworkError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        })

        forgotPasswordViewModel.showUnknownError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        })

        forgotPasswordViewModel.showServerError.observe(this) { errorMessage ->
            binding.progressBarContainer.visibility = View.GONE
            Log.d("Login Error", errorMessage)
        }

    }

    private fun initViews() {

        binding.progressBarContainer.visibility = View.GONE
        binding.btnSave.setOnClickListener {
            val emailId = binding.etEmailID.text.toString()

            if (binding.etEmailID.checkValidations(FieldEnum.EMAIL_ID.fieldName)) {
                binding.progressBarContainer.visibility = View.VISIBLE
                forgotPasswordViewModel.forgotPasswordUser(emailId)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForgotPasswordBinding
        get() = FragmentForgotPasswordBinding::inflate
}

