package com.sendbizcard.ui.forgotpassword

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentCreateNewPasswordBinding
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
        forgotPasswordViewModel.changePasswordResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarContainer.visibility = View.GONE
            showSuccessDialog()
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
            var isValidationPassed = false
            val password = binding.etNewPassword.text.toString()
            val confpassword = binding.etConfirmPassword.text.toString()

            isValidationPassed =  binding.etNewPassword.checkValidations(FieldEnum.PASSWORD.fieldName)
            isValidationPassed =  binding.etConfirmPassword.checkValidations(FieldEnum.CONFIRM_PASSWORD.fieldName)

            if (isValidationPassed) {
                binding.progressBarContainer.visibility = View.VISIBLE
                forgotPasswordViewModel.changePasswordUser(otp, password, confpassword)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showSuccessDialog() {
        AlertDialogWithImageView.showDialog(
            requireFragmentManager().beginTransaction(),
            requireContext(),
            requireContext().resources.getString(R.string.success_title),
            if (isChangePassword){
                requireContext().resources.getString(R.string.success_title_new_password)
            }else{
                requireContext().resources.getString(R.string.success_title_change_password)
            }
            ,
            R.drawable.ic_success,
            onDismiss = {
                if (fragmentManager != null) {
                    if (isChangePassword){
                        var intent = Intent(requireContext(), HomeActivity::class.java)
                        startActivity(intent)

                    }else{
                        findNavController().navigate(R.id.nav_login,null,
                            getDefaultNavigationAnimation())
                    }
                }
            }
        )


    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCreateNewPasswordBinding
        get() = FragmentCreateNewPasswordBinding::inflate
}