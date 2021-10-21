package com.sendbizcard.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.sendbizcard.HomeActivity
import com.sendbizcard.ui.main.MainActivity
import com.sendbizcard.base.BaseActivity
import com.sendbizcard.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
        splashViewModel.fetchAndActivateConfigParameters()
    }

    private fun observeData() {
        splashViewModel.configParsed.observe(this) { isConfigParsed ->
            if (isConfigParsed) {
                val intent = if (splashViewModel.checkIfUserIsLoggedIn()) {
                    Intent(this@SplashActivity, HomeActivity::class.java)
                } else {
                    Intent(this@SplashActivity, MainActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate
}