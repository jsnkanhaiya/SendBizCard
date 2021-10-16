package com.sendbizcard.ui.otp

import android.R.attr
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentChangePasswordVerificationBinding
import com.sendbizcard.databinding.FragmentForgotPasswordBinding
import com.sendbizcard.databinding.FragmentVerifyForgotPasswordBinding
import com.sendbizcard.utils.AlertDialogWithImageView
import dagger.hilt.android.AndroidEntryPoint
import android.R.attr.defaultValue

import android.R.attr.key
import android.text.Editable
import android.widget.Toast


@AndroidEntryPoint
class VerifyForgotPasswordOtpFragment : BaseFragment<FragmentVerifyForgotPasswordBinding>() {
    private val TAG = "VerifyOtpFragment"

    private val verifyOtpViewModel: VerifyOtpViewModel by viewModels()
    private var _binding: FragmentVerifyForgotPasswordBinding? = null
    private  var otp= ""
    private  var email= ""
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      // WeightFragmentArgs.fromBundle(requireArguments()).weightRecordingTaskId


        _binding = getViewBinding()
        initViews()
        setupObservers()
        val bundle = this.arguments
        if (bundle != null) {
             otp = bundle.getString("otp").toString()
            //binding.otpPinView.text=
            Toast.makeText(context,"Otp is "+otp ,Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupObservers() {
        verifyOtpViewModel.otpResponseModel.observe(viewLifecycleOwner, Observer {
            showSuccessDialog()
        })
    }

    private fun initViews() {
        binding.btnSave.setOnClickListener {
             otp = binding.otpPinView.text.toString()
            if (verifyOtpViewModel.isValidForgotOtpData(otp)){
                verifyOtpViewModel.verifyForGotOtp(otp,email)
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
                    findNavController().navigate(R.id.nav_createNewpassword, bundleOf("otp" to otp))
                }
            }
        )


    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentVerifyForgotPasswordBinding
        get() = FragmentVerifyForgotPasswordBinding::inflate


}