package com.sendbizcard.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sendbizcard.R
import com.sendbizcard.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {


    private val TAG = "SplashFragment"
    private lateinit var binding: FragmentSplashBinding
    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[SplashViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_splash, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }


    private fun setupObservers() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withDelayOnMain(3000){
           // findNavController().navigate()
        }
    }

    override fun onResume() {
        super.onResume()


    }

    fun withDelayOnMain(delay: Long, block: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(Runnable(block), delay)
    }
}