package com.sendbizcard.ui.otp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentChangePasswordVerificationBinding
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.utils.AlertDialogWithImageView
import com.sendbizcard.utils.getDefaultNavigationAnimation
import com.sendbizcard.utils.gone
import com.sendbizcard.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import me.gujun.android.span.span

@AndroidEntryPoint
class VerifyOtpFragment : BaseFragment<FragmentChangePasswordVerificationBinding>() {

    private val verifyOtpViewModel: VerifyOtpViewModel by viewModels()
    private lateinit var binding: FragmentChangePasswordVerificationBinding
    private var otp = ""

    @RequiresApi(Build.VERSION_CODES.M)
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
            +"Dont receive the OTP?"
            span {
                text =" "+ "RESEND OTP"
                textColor = ResourcesCompat.getColor(
                    requireContext().resources,
                    R.color.colorPrimary3,
                    null
                )
                onClick = {
                    showProgressBar()
                    verifyOtpViewModel.resendOTP()

                }
            }
        }

        binding.cbprivacy.movementMethod =
            LinkMovementMethod.getInstance() // without LinkMovementMethod, click will not work
        binding.cbprivacy.text = span {
            +"By creating an account you agree to our"
            span {
                text = "Terms"
                textColor = ResourcesCompat.getColor(
                    requireContext().resources,
                    R.color.colorPrimary3,
                    null
                )
                onClick = {
                    //on click

                }
            }
            span { +" of service and " }
            span {
                text = "Privacy Policy"
                textColor = ResourcesCompat.getColor(
                    requireContext().resources,
                    R.color.colorPrimary3,
                    null
                )
                onClick = {
                    //on click
                    findNavController().navigate(
                        R.id.nav_privacy, null,
                        getDefaultNavigationAnimation()
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupObservers() {
        verifyOtpViewModel.otpResponseModel.observe(this) {
            hideProgressBar()
            showSuccessDialog()
        }
        verifyOtpViewModel.showNetworkError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        verifyOtpViewModel.otpResponseReSendModel.observe(this) {
            hideProgressBar()
        }

        verifyOtpViewModel.showUnknownError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        verifyOtpViewModel.showServerError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage.errorMessage)
        }
    }

    private fun showProgressBar() {
        binding.progressBarContainer.visible()
    }

    private fun hideProgressBar() {
        binding.progressBarContainer.gone()
    }

    private fun showErrorMessage(errorMessage: String) {
        hideProgressBar()
        val fragment = CommonDialogFragment.newInstance(
            resources.getString(R.string.error),
            errorMessage, "", R.drawable.ic_icon_error
        )
        fragment.show(parentFragmentManager, "VerifyOTPFragment")
    }

    private fun initViews() {
        hideProgressBar()

        val bundle = this.arguments
        if (bundle != null) {
            otp = bundle.getString("otp").toString()
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        binding.btnverify.setOnClickListener {

            if (verifyOtpViewModel.isValidOtpData(otp)) {
                if (binding.cbprivacy.isChecked) {
                    binding.tvOtpError.gone()
                    showProgressBar()
                    verifyOtpViewModel.verifyOtp(otp)
                } else {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.select_terms),
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else {
                binding.tvOtpError.visible()
                binding.tvOtpError.text = resources.getString(R.string.enter_otp6)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showSuccessDialog() {
        AlertDialogWithImageView.showDialog(
            requireFragmentManager().beginTransaction(),
            requireContext(),
            requireContext().resources.getString(R.string.success_title),
            requireContext().resources.getString(R.string.success_title_sub),
            R.drawable.ic_success_tick,
            onDismiss = {
                if (fragmentManager != null) {
                    findNavController().navigate(R.id.nav_select_theme,null,
                        getDefaultNavigationAnimation())
                }
            }
        )
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentChangePasswordVerificationBinding
        get() = FragmentChangePasswordVerificationBinding::inflate

}