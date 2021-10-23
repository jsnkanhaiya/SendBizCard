package com.sendbizcard.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentRegisterBinding
import com.sendbizcard.dialog.ConfirmationDialogFragment
import com.sendbizcard.utils.*
import dagger.hilt.android.AndroidEntryPoint
import me.gujun.android.span.span

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initViews()
        setUpObservers()
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
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun setUpObservers() {
        registerViewModel.registerReponse.observe(viewLifecycleOwner, {
            binding.progressBarContainer.gone()
            findNavController().navigate(R.id.nav_verifyOtp, bundleOf("otp" to it.data?.contactOtp.toString()))

        })

        registerViewModel.showUnknownError.observe(this, {
            binding.progressBarContainer.gone()
            showlogoutDialog(it)
        })

        registerViewModel.showNetworkError.observe(this, {
            binding.progressBarContainer.gone()
            showlogoutDialog(it)
        })

        registerViewModel.showServerError.observe(this, {
            binding.progressBarContainer.gone()
            showlogoutDialog(it)
        })
    }

    private fun initViews() {
        binding.btnSave.setOnClickListener {

            var isValidationPassed =false
            val name = binding.etName.text.toString()
            val emailId = binding.etEmailID.text.toString()
            val mobileNo = binding.etMobile.text.toString()
            val password = binding.etPassword.text.toString()
            val confPassword = binding.etConfirmPassword.text.toString()

            isValidationPassed = binding.etName.checkValidations(FieldEnum.NAME.fieldName)
            isValidationPassed = binding.etEmailID.checkValidations(FieldEnum.EMAIL_ID.fieldName)
            isValidationPassed = binding.etMobile.checkValidations(FieldEnum.MOBILE_NUMBER.fieldName)
            isValidationPassed =  binding.etPassword.checkValidations(FieldEnum.PASSWORD.fieldName)
            isValidationPassed =  binding.etConfirmPassword.checkValidations(FieldEnum.CONFIRM_PASSWORD.fieldName)


            if (isValidationPassed) {
                binding.progressBarContainer.visible()
                registerViewModel.registerUser(name, mobileNo, emailId, password, confPassword)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        get() = FragmentRegisterBinding::inflate


    private fun showlogoutDialog(message: String) {
        val confirmationDialogFragment = ConfirmationDialogFragment.Builder()
            .setAcceptButton(
                context?.resources?.getString(R.string.ok_confirmation)!!
            )
            .setTitle(
                context?.resources?.getString(R.string.error)!!
            )
            .setMessage(
                message
            )
            .setMessageTextColor(requireContext().resources.getColor(R.color.textcolour))
            .build()
        confirmationDialogFragment.show(
            requireActivity().supportFragmentManager,
            ConfirmationDialogFragment.TAG
        )

        confirmationDialogFragment.setButtonClickListener(object :
            ConfirmationDialogFragment.OnButtonClickListener {
            override fun onAcceptClick() {
                confirmationDialogFragment.dismiss()
            }

            override fun onRejectClick() {
            }
        })

    }


    fun isValidRegisterData(
        name: String,
        mobileNo: String,
        emailId: String,
        password: String,
        confPassword: String
    ): Boolean {
        return when {
            name.isBlank() -> {
                binding.textInputName.error= resources.getString(R.string.enter_emailID)
                false
            }
            mobileNo.isBlank() -> {
                binding.textInputMobile.error= resources.getString(R.string.enter_emailID)
                false
            }
            mobileNo.length<10 || mobileNo.length>10-> {
                binding.textInputMobile.error= resources.getString(R.string.enter_emailID)
                false
            }
            emailId.isBlank() -> {
                binding.textInputEmail.error= resources.getString(R.string.enter_emailID)
                false
            }
            confPassword.isBlank() -> {
                binding.textInputEmail.error= resources.getString(R.string.enter_emailID)
                false
            }
            ValidationUtils.isRequiredPasswordLengthForLogin(password) -> {
                binding.textInputEmail.error= resources.getString(R.string.enter_emailID)
                false
            }
            ValidationUtils.isRequiredPasswordLengthForLogin(confPassword) -> {
                binding.textInputEmail.error= resources.getString(R.string.enter_emailID)
                false
            }
            else -> {
                if (ValidationUtils.isBothPasswordMatch(password, confPassword)) {
                    return true
                } else {
                    binding.textInputEmail.error= resources.getString(R.string.enter_emailID)
                    return false
                }
            }
        }

    }
}