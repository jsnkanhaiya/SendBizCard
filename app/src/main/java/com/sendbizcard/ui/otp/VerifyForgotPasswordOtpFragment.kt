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
import com.sendbizcard.utils.AlertDialogWithImageView
import com.sendbizcard.utils.getDefaultNavigationAnimation
import com.sendbizcard.utils.showErrorDialog
import com.sendbizcard.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import me.gujun.android.span.span


@AndroidEntryPoint
class VerifyForgotPasswordOtpFragment : BaseFragment<FragmentVerifyForgotPasswordBinding>() {
    private val TAG = "VerifyOtpFragment"

    private val verifyOtpViewModel: VerifyOtpViewModel by viewModels()
    private var _binding: FragmentVerifyForgotPasswordBinding? = null
    private var otp = ""
    private var email = ""
    var isChangePassword=false

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
        _binding = getViewBinding()
        initViews()
        initSpanUI()
        setupObservers()
        val bundle = this.arguments
        if (bundle != null) {
            otp = bundle.getString("otp").toString()
            //binding.otpPinView.text=
            Toast.makeText(context, "Otp is " + otp, Toast.LENGTH_LONG).show()
        }
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
                    binding.progressBarContainer.visible()
                    verifyOtpViewModel.resendOTP()

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

        verifyOtpViewModel.otpResponseReSendModel.observe(viewLifecycleOwner, Observer {
            binding.progressBarContainer.visibility = View.GONE
           /// showSuccessDialog()
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {

        val bundle = this.arguments
        if (bundle != null) {
             isChangePassword = bundle.getBoolean("isChangepassword")
            //binding.otpPinView.text=
            Toast.makeText(context, "isChangePassword is " + isChangePassword, Toast.LENGTH_LONG).show()
        }

        if (isChangePassword){
            binding.tvTitle.text = resources.getString(R.string.create_new_password_verfication)
        }

        binding.progressBarContainer.visibility = View.GONE
        binding.btnSave.setOnClickListener {
            otp = binding.otpPinView.text.toString()
            if (verifyOtpViewModel.isValidForgotOtpData(otp)) {
                binding.progressBarContainer.visibility = View.VISIBLE
                 verifyOtpViewModel.verifyForGotOtp(otp,email)
               // showSuccessDialog()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showSuccessDialog() {
        binding.progressBarContainer.visibility = View.GONE
        AlertDialogWithImageView.showDialog(
            requireFragmentManager().beginTransaction(),
            requireContext(),
            requireContext().resources.getString(R.string.success_title),
            requireContext().resources.getString(R.string.success_title_sub),
            R.drawable.ic_success,
            onDismiss = {
                if (fragmentManager != null) {
                    findNavController().navigate(R.id.nav_createNewpassword, bundleOf("otp" to otp))
                }
            }
        )


    }



    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentVerifyForgotPasswordBinding
        get() = FragmentVerifyForgotPasswordBinding::inflate


}