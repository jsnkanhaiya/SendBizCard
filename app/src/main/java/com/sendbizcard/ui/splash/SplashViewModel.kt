package com.sendbizcard.ui.splash

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.sendbizcard.repository.ApiRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl
) : ViewModel(), LifecycleObserver {

    var isUserLoggedIn: Boolean
        get() = apiRepositoryImpl.isUserLoggedIn
        set(value) {
            apiRepositoryImpl.isUserLoggedIn = value
        }
}