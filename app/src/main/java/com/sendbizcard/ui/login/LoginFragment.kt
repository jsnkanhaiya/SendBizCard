package com.sendbizcard.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentLoginBinding
import com.sendbizcard.dialog.CommonDialogFragment
import com.sendbizcard.utils.*
import dagger.hilt.android.AndroidEntryPoint
import me.gujun.android.span.span

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initOnClicks()
        initSpanUI()
        setupObservers()
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
                    //on click
                    findNavController().navigate(
                        R.id.nav_sign_up, null,
                        getDefaultNavigationAnimation()
                    )
                }
            }
        }
    }

    private fun setupObservers() {
        loginViewModel.loginResponse.observe(this) {
            hideProgressBar()
            findNavController().navigate(R.id.nav_select_theme,null, getDefaultNavigationAnimation())
           /* val i = Intent(requireContext(), HomeActivity::class.java)
            requireActivity().startActivity(i)
            requireActivity().finish()*/
        }

        loginViewModel.showNetworkError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        loginViewModel.showUnknownError.observe(this) { errorMessage ->
            showErrorMessage(errorMessage)
        }

        loginViewModel.showServerError.observe(this) { serverError ->
            showErrorMessage(serverError.errorMessage)
        }
    }

    private fun showErrorMessage(errorMessage: String) {
        hideProgressBar()
        val fragment = CommonDialogFragment.newInstance(resources.getString(R.string.error),
            errorMessage,"",R.drawable.ic_icon_error)
        fragment.show(parentFragmentManager,"LoginFragment")
    }

    private fun initOnClicks() {
        binding.btnSave.setOnClickListener {
            val emailId = binding.etEmailID.text.toString()
            val password = binding.etPassword.text.toString()

            val isEmailIdValidated = binding.etEmailID.checkValidations(FieldEnum.EMAIL_ID.fieldName)
            val isPasswordValidated =  binding.etPassword.checkValidations(FieldEnum.PASSWORD.fieldName)

            if (isEmailIdValidated && isPasswordValidated) {
                showProgressBar()
                loginViewModel.login(emailId, password)
            }


            /*when {
                emailId.isEmpty() -> {
                    binding.textInputEmail.error= resources.getString(R.string.enter_emailID)
                   // showErrorDialog("Enter Email Id", requireActivity(), requireContext())
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    binding.textInputPassword.error= resources.getString(R.string.enter_password)
                  //  showErrorDialog("Enter Password", requireActivity(), requireContext())
                    return@setOnClickListener
                }
                else -> {
                    binding.textInputEmail.error= null
                    binding.textInputPassword.error= null
                    binding.progressBarContainer.visibility = View.VISIBLE
                    loginViewModel.login(emailId, password)
                }
            }

            if (loginViewModel.isValidLoginData(emailId, password)) {
                binding.progressBarContainer.visibility = View.VISIBLE
                loginViewModel.login(emailId, password)
            }*/
        }

        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.nav_forgot_password)
        }
    }

    private fun showProgressBar() {
        binding.progressBarContainer.visible()
    }

    private fun hideProgressBar() {
        binding.progressBarContainer.gone()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate


}