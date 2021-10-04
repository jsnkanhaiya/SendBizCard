package com.sendbizcard.ui.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sendbizcard.databinding.FragmentCreateNewPasswordBinding

class CreateNewPasswordFragment : Fragment(){


    private val TAG = "CreateNewPasswordFragment"

    private lateinit var forgotPasswordViewmodel: ForgotPasswordViewModel
    private var _binding: FragmentCreateNewPasswordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var otp =""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        forgotPasswordViewmodel =
            ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)

        _binding = FragmentCreateNewPasswordBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()
    }

    private fun setupObservers() {
        forgotPasswordViewmodel.changePasswordResponse.observe(viewLifecycleOwner, Observer {

        })
    }

    private fun initViews() {
        binding.btnSave.setOnClickListener {
            val password = binding.etNewPassword.text.toString()
            val confpassword = binding.etConfirmPassword.text.toString()
            if (forgotPasswordViewmodel.isValidChangePasswordData(otp,password,confpassword)){
                forgotPasswordViewmodel.changePasswordUser(otp,password,confpassword)
            }
        }
    }
}