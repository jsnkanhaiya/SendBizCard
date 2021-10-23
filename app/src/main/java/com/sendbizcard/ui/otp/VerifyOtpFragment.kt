package com.sendbizcard.ui.otp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentChangePasswordVerificationBinding
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.utils.*
import dagger.hilt.android.AndroidEntryPoint
import me.gujun.android.span.span

@AndroidEntryPoint
class VerifyOtpFragment : BaseFragment<FragmentChangePasswordVerificationBinding>() {

    private val verifyOtpViewModel: VerifyOtpViewModel by viewModels()
    private lateinit var binding: FragmentChangePasswordVerificationBinding
    private  var otp= ""


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initViews()
        initSpanUI()
        setupObservers()
    }


    private fun initSpanUI() {
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
            span{+" of service and "}
            span {
                text = "Privacy Policy"
                textColor = ResourcesCompat.getColor(
                    requireContext().resources,
                    R.color.colorPrimary3,
                    null
                )
                onClick = {
                    //on click
                    findNavController().navigate(R.id.nav_privacy,null,
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

    private fun initViews() {
        hideProgressBar()
        binding.btnverify.setOnClickListener {
            if (verifyOtpViewModel.isValidOtpData(otp)&& binding.cbprivacy.isChecked){
                showProgressBar()
                verifyOtpViewModel.verifyOtp(otp)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showSuccessDialog() {
            AlertDialogWithImageView.showDialog(
                requireFragmentManager().beginTransaction(),
                requireContext(),
                requireContext().resources.getString(R.string.success_title)
                ,
                requireContext().resources.getString(R.string.success_title_sub),
                R.drawable.ic_success_tick,
                onDismiss = {
                    if(fragmentManager!= null) {
                        val i = Intent(requireContext(),HomeActivity::class.java)
                        startActivity(i)
                    }
                }
            )


    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentChangePasswordVerificationBinding
        get() = FragmentChangePasswordVerificationBinding::inflate

}