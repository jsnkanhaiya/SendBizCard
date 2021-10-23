package com.sendbizcard.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentLoginBinding
import com.sendbizcard.dialog.ConfirmationDialogFragment
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
        loginViewModel.loginReponse.observe(viewLifecycleOwner, Observer {
            hideProgressBar()
            val i = Intent(requireContext(), HomeActivity::class.java)
            requireActivity().startActivity(i)
            requireActivity().finish()
        })

        loginViewModel.showNetworkError.observe(this, Observer {
            hideProgressBar()
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        })

        loginViewModel.showUnknownError.observe(this, Observer {
            hideProgressBar()
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        })


        loginViewModel.error.observe(viewLifecycleOwner, Observer {
            hideProgressBar()
            context?.let { it1 -> showErrorDialog(it, requireActivity(), it1) }
        })

        loginViewModel.showServerError.observe(this) { errorMessage ->
            hideProgressBar()
        }
    }

    private fun initOnClicks() {
        binding.btnSave.setOnClickListener {
            val emailId = binding.etEmailID.text.toString()
            val password = binding.etPassword.text.toString()

            if (binding.etEmailID.checkValidations(FieldEnum.MOBILE_NUMBER.fieldName) &&
                binding.etPassword.checkValidations(FieldEnum.PASSWORD.fieldName)
            ) {
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