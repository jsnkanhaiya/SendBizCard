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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentRegisterBinding
import com.sendbizcard.dialog.ConfirmationDialogFragment
import com.sendbizcard.utils.getDefaultNavigationAnimation
import dagger.hilt.android.AndroidEntryPoint
import me.gujun.android.span.span

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private  val registerViewModel: RegisterViewModel by viewModels()
    private var binding: FragmentRegisterBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        initViews()
        setUpObservers()
        initSpanUI()
    }

    private fun initSpanUI() {
        binding?.tvAlreadyAccount?.movementMethod =
            LinkMovementMethod.getInstance() // without LinkMovementMethod, click will not work
        binding?.tvAlreadyAccount?.text = span {
            +resources.getString(R.string.alreadyAccount)
            span {
                text = " "+ resources.getString(R.string.signin)
                textColor = ResourcesCompat.getColor(
                    requireContext().resources,
                    R.color.colorPrimary3,
                    null
                )
                onClick = {
                    //on click
                    findNavController().navigate(R.id.nav_login,null,
                        getDefaultNavigationAnimation()
                    )
                }
            }
        }
    }

    private fun setUpObservers() {
        registerViewModel.registerReponse.observe(viewLifecycleOwner, Observer {
                findNavController().navigate(R.id.nav_verifyOtp, bundleOf("otp" to it.data?.contactOtp))

        })

        registerViewModel.showUnknownError.observe(this, Observer {
            showlogoutDialog(it)
        })

        registerViewModel.showNetworkError.observe(this, Observer {
            showlogoutDialog(it)
        })

        registerViewModel.showServerError.observe(this, Observer {
            showlogoutDialog(it)
        })
    }

    private fun initViews() {
        binding?.btnSave?.setOnClickListener {
            val name = binding?.etName?.text.toString()
            val emailId = binding?.etEmailID?.text.toString()
            val mobileNo = binding?.etMobile?.text.toString()
            val password = binding?.etPassword?.text.toString()
            val confPassword = binding?.etConfirmPassword?.text.toString()
            if (registerViewModel.isValidRegisterData(
                    name,
                    mobileNo,
                    emailId,
                    password,
                    confPassword
                )
            ) {
                registerViewModel.registerUser(name, mobileNo, emailId, password, confPassword)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        get() = FragmentRegisterBinding::inflate


    private fun showlogoutDialog(message:String) {
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


}