package com.sendbizcard.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sendbizcard.R
import com.sendbizcard.databinding.FragmentSiginupBinding
import com.sendbizcard.databinding.FragmentSlideshowBinding
import com.sendbizcard.ui.slideshow.SlideshowViewModel

class SignUpFragment : Fragment() {

    private val TAG = "SignUpFragment"

    private lateinit var signUpViewModel: SignUpViewModel
    private var _binding: FragmentSiginupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signUpViewModel =
            ViewModelProvider(this).get(SignUpViewModel::class.java)

        _binding = FragmentSiginupBinding.inflate(inflater, container, false)
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
        binding.btnSave.setOnClickListener {

        }
    }


}