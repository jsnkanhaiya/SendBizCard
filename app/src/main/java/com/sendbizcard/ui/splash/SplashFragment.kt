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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sendbizcard.HomeActivity
import com.sendbizcard.R
import com.sendbizcard.base.BaseFragment
import com.sendbizcard.databinding.FragmentSlideshowBinding
import com.sendbizcard.databinding.FragmentSplashBinding
import com.sendbizcard.ui.slideshow.SlideshowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val TAG = "SplashFragment"
    private var binding: FragmentSplashBinding? = null
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewBinding()
        observeData()
        splashViewModel.fetchAndActivateConfigParameters()

    }

    private fun observeData() {
        splashViewModel.isConfigParsed.observe(this, Observer { configParsed ->
            if (configParsed) {
                if (splashViewModel.checkIfUserIsLoggedIn()){
                    val intent = Intent(requireContext(),HomeActivity::class.java)
                    startActivity(intent)
                }else{
                    findNavController().navigate(R.id.nav_login)
                }
            }
        })
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate
}