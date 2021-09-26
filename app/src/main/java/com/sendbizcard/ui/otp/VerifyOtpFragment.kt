package com.sendbizcard.ui.otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sendbizcard.databinding.FragmentChangePasswordVerificationBinding


class VerifyOtpFragment : Fragment() {

    private val TAG = "VerifyOtpFragment"

    private lateinit var verifyOtpViewModel: VerifyOtpViewModel
    private var _binding: FragmentChangePasswordVerificationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        verifyOtpViewModel =
            ViewModelProvider(this).get(VerifyOtpViewModel::class.java)

        _binding = FragmentChangePasswordVerificationBinding.inflate(inflater, container, false)
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

    }

    private fun initViews() {
        binding.btnverify.setOnClickListener {
            if (verifyOtpViewModel.isValidOtp()){
                verifyOtpViewModel.verifyOtp()
            }
        }
    }

}