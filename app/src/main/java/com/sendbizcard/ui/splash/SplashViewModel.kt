package com.sendbizcard.ui.splash

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.sendbizcard.repository.ApiRepositoryImpl
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl
) : ViewModel(), LifecycleObserver {

    var isUserLoggedIn: Boolean
        get() = apiRepositoryImpl.isUserLoggedIn
        set(value) {
            apiRepositoryImpl.isUserLoggedIn = value
        }
}