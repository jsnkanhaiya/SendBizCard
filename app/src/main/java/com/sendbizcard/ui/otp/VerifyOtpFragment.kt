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
import com.sendbizcard.utils.AlertDialogWithImageView
import com.sendbizcard.utils.getDefaultNavigationAnimation
import com.sendbizcard.utils.showErrorDialog
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
        verifyOtpViewModel.otpResponseModel.observe(viewLifecycleOwner, Observer {
            binding.progressBarContainer.visibility = View.GONE
            showSuccessDialog()
        })
        verifyOtpViewModel.showNetworkError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it,requireActivity(), it1) }
        })

        verifyOtpViewModel.showUnknownError.observe(this, Observer {
            binding.progressBarContainer.visibility = View.GONE
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        })

        verifyOtpViewModel.showServerError.observe(this ) { errorMessage ->
            binding.progressBarContainer.visibility = View.GONE
            Log.d("Login Error",errorMessage)
        }
    }

    private fun initViews() {
        binding.progressBarContainer.visibility = View.GONE
        binding.btnverify.setOnClickListener {
            if (verifyOtpViewModel.isValidOtpData(otp)&& binding.cbprivacy.isChecked){
                binding.progressBarContainer.visibility = View.VISIBLE
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
                        var i = Intent(requireContext(),HomeActivity::class.java)
                        startActivity(i)
                    }
                }
            )


    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentChangePasswordVerificationBinding
        get() = FragmentChangePasswordVerificationBinding::inflate

}