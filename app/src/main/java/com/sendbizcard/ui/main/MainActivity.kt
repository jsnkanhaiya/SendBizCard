package com.sendbizcard.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.sendbizcard.R
import com.sendbizcard.base.BaseActivity
import com.sendbizcard.databinding.ActivityMainBinding
import com.sendbizcard.prefs.PreferenceSourceImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {


    private lateinit var binding: ActivityMainBinding

    private val mainViewModel : MainViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        val host = findNavController(R.id.fragment_nav_host)
        if (mainViewModel.checkIfFirstTimeUser()) {
            host.navigate(R.id.nav_intro)
        } else {
            host.navigate(R.id.nav_login)
        }
    }
}