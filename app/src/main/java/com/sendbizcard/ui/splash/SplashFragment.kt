package com.sendbizcard.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentSlideshowBinding
import com.sendbizcard.databinding.FragmentSplashBinding
import com.sendbizcard.ui.slideshow.SlideshowViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val TAG = "SplashFragment"
    private var _binding: FragmentSplashBinding? = null
    private lateinit var splashViewModel: SplashViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        splashViewModel =
            ViewModelProvider(this).get(SplashViewModel::class.java)

        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    private fun setupObservers() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withDelayOnMain(3000){
            if (splashViewModel.isUserLoggedIn){
                var i = Intent(requireContext(),HomeActivity::class.java)
                startActivity(i)
            }else{
                findNavController().navigate(R.id.nav_login)
            }

        }
    }


    fun withDelayOnMain(delay: Long, block: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(Runnable(block), delay)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate
}