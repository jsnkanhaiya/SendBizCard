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
import dagger.hilt.android.AndroidEntryPoint
import me.gujun.android.span.span


@AndroidEntryPoint
class VerifyForgotPasswordOtpFragment : BaseFragment<FragmentVerifyForgotPasswordBinding>() {
    private val TAG = "VerifyOtpFragment"

    private val verifyOtpViewModel: VerifyOtpViewModel by viewModels()
    private var _binding: FragmentVerifyForgotPasswordBinding? = null
    private var otp = ""
    private var email = ""

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
        binding.tvAlreadyAccount.movementMethod =
            LinkMovementMethod.getInstance() // without LinkMovementMethod, click will not work
        binding.tvAlreadyAccount.text = span {
            +"Don't have an account? "
            span {
                text = "Create New Account"
                textColor = ResourcesCompat.getColor(
                    requireContext().resources,
                    R.color.colorPrimary3,
                    null
                )
                onClick = {
                    //on click api call resend

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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {
        binding.progressBarContainer.visibility = View.GONE
        binding.btnSave.setOnClickListener {
            otp = binding.otpPinView.text.toString()
            if (verifyOtpViewModel.isValidForgotOtpData(otp)) {
                // verifyOtpViewModel.verifyForGotOtp(otp,email)
                binding.progressBarContainer.visibility = View.VISIBLE
                showSuccessDialog()
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
            R.drawable.ic_success_tick,
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