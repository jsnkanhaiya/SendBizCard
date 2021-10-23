package com.sendbizcard.ui.otp

import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentVerifyForgotPasswordBinding
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.utils.*
import dagger.hilt.android.AndroidEntryPoint
import me.gujun.android.span.span


@AndroidEntryPoint
class VerifyForgotPasswordOtpFragment : BaseFragment<FragmentVerifyForgotPasswordBinding>() {

    private val verifyOtpViewModel: VerifyOtpViewModel by viewModels()
    private lateinit var binding: FragmentVerifyForgotPasswordBinding
    private var otp = ""
    private var email = ""
    var isChangePassword=false


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initViews()
        initSpanUI()
        setupObservers()
    }


    private fun initSpanUI() {
        binding.tvSendOtp.movementMethod =
            LinkMovementMethod.getInstance() // without LinkMovementMethod, click will not work
        binding.tvSendOtp.text = span {
            +resources.getString(R.string.dont_receive_email)
            span {
                text =" "+ "Send again"
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
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupObservers() {
        verifyOtpViewModel.otpResponseModel.observe(this) {
            hideProgressBar()
            showSuccessDialog()
        }

        verifyOtpViewModel.otpResponseReSendModel.observe(this) {
            hideProgressBar()
        }

        verifyOtpViewModel.showNetworkError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        verifyOtpViewModel.showUnknownError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        verifyOtpViewModel.showServerError.observe(this ) { errorMessage ->
            showErrorMessage(errorMessage)
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
        val fragment = CommonDialogFragment.newInstance(resources.getString(R.string.error),
            errorMessage,"",R.drawable.ic_icon_error)
        fragment.show(parentFragmentManager,"VerifyOTPFragment")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {

        val bundle = this.arguments
        if (bundle != null) {
             isChangePassword = bundle.getBoolean("isChangepassword")
            email = bundle.getString("emailID","")
            otp = bundle.getString("otp").toString()
            Toast.makeText(context, "Otp is " + otp, Toast.LENGTH_LONG).show()
           // Toast.makeText(context, "isChangePassword is " + isChangePassword, Toast.LENGTH_LONG).show()
           // Toast.makeText(context, "email id  is " + email, Toast.LENGTH_LONG).show()
        }


        if (isChangePassword){
            binding.tvTitle.text = resources.getString(R.string.create_new_password_verfication)
        }

        hideProgressBar()
        binding.btnSave.setOnClickListener {
            otp = binding.otpPinView.text.toString()
            if (verifyOtpViewModel.isValidForgotOtpData(otp)) {
                showProgressBar()
                 verifyOtpViewModel.verifyForGotOtp(otp,email)
              //  Toast.makeText(context, "email id  is " + email  +"otp is "+ otp, Toast.LENGTH_LONG).show()
               // showSuccessDialog()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showSuccessDialog() {
        hideProgressBar()
        AlertDialogWithImageView.showDialog(
            requireFragmentManager().beginTransaction(),
            requireContext(),
            requireContext().resources.getString(R.string.success_title),
            requireContext().resources.getString(R.string.success_title_sub),
            R.drawable.ic_success,
            onDismiss = {
                if (fragmentManager != null) {
                    findNavController().navigate(R.id.nav_createNewpassword, bundleOf("otp" to otp,"isChangepassword" to isChangePassword),
                        getDefaultNavigationAnimation())
                }
            }
        )


    }



    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentVerifyForgotPasswordBinding
        get() = FragmentVerifyForgotPasswordBinding::inflate


}