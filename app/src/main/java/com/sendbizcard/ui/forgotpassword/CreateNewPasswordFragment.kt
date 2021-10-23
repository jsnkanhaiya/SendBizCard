package com.sendbizcard.ui.forgotpassword

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentCreateNewPasswordBinding
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateNewPasswordFragment : BaseFragment<FragmentCreateNewPasswordBinding>() {

    private val forgotPasswordViewModel: ForgotPasswordViewModel by viewModels()
    private lateinit var binding: FragmentCreateNewPasswordBinding
    var otp = ""
    var isChangePassword = false


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initViews()
        setupObservers()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupObservers() {
        forgotPasswordViewModel.changePasswordResponse.observe(this) {
            hideProgressBar()
            showSuccessDialog()
        }

        forgotPasswordViewModel.showNetworkError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        forgotPasswordViewModel.showUnknownError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        forgotPasswordViewModel.showServerError.observe(this) { errorMessage ->
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
        val fragment = CommonDialogFragment.newInstance(
            resources.getString(R.string.error),
            errorMessage, "", R.drawable.ic_icon_error
        )
        fragment.show(parentFragmentManager, "CreateNewPasswordFragment")
    }

    private fun initViews() {
        binding.progressBarContainer.visibility = View.GONE
        val bundle = this.arguments
        if (bundle != null) {
            otp = bundle.getString("otp").toString()
            //binding.otpPinView.text=
            // Toast.makeText(context, "Otp is " + otp, Toast.LENGTH_LONG).show()
        }
        if (bundle != null) {
            isChangePassword = bundle.getBoolean("isChangepassword")
            //binding.otpPinView.text=
            //  Toast.makeText(context, "isChangePassword is " + isChangePassword, Toast.LENGTH_LONG).show()
        }

        if (isChangePassword) {
            binding.tvTitle.text = resources.getString(R.string.create_new_password)
        }

        binding.btnSave.setOnClickListener {

            val password = binding.etNewPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            val isValidationPassedPassword =
                binding.etNewPassword.checkValidations(FieldEnum.PASSWORD.fieldName)
            val isValidationPassedConfirmPassword =
                binding.etConfirmPassword.checkValidations(FieldEnum.PASSWORD.fieldName)

            if (isValidationPassedConfirmPassword){
                val isConfirmPasswordValidated = binding.etConfirmPassword.checkValidations(FieldEnum.CONFIRM_PASSWORD.fieldName,password)
                if (isValidationPassedPassword && isValidationPassedConfirmPassword && isConfirmPasswordValidated) {
                    showProgressBar()
                    forgotPasswordViewModel.changePasswordUser(otp, password, confirmPassword)
                }

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showSuccessDialog() {
        AlertDialogWithImageView.showDialog(
            requireFragmentManager().beginTransaction(),
            requireContext(),
            requireContext().resources.getString(R.string.success_title),
            if (isChangePassword) {
                requireContext().resources.getString(R.string.success_title_new_password)
            } else {
                requireContext().resources.getString(R.string.success_title_change_password)
            },
            R.drawable.ic_success,
            onDismiss = {
                if (fragmentManager != null) {
                    if (isChangePassword) {
                        var intent = Intent(requireContext(), HomeActivity::class.java)
                        startActivity(intent)

                    } else {
                        findNavController().navigate(
                            R.id.nav_login, null,
                            getDefaultNavigationAnimation()
                        )
                    }
                }
            }
        )


    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCreateNewPasswordBinding
        get() = FragmentCreateNewPasswordBinding::inflate
}