package com.sendbizcard.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sendbizcard.R
import com.sendbizcard.databinding.FragmentSiginupBinding

class SignUpFragment : Fragment() {
    private val TAG = "SignUpFragment"
    private lateinit var binding: FragmentSiginupBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[SignUpViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_siginup, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()
    }

    private fun setupObservers() {

    }

    private fun initViews() {
        binding.btnSave.setOnClickListener {

        }
    }


}