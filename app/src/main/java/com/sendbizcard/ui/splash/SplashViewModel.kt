package com.sendbizcard.ui.splash

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val preferenceSourceImpl: PreferenceSourceImpl
) : ViewModel(), LifecycleObserver {



    fun checkIfUserIsLoggedIn() : Boolean {
        return preferenceSourceImpl.isUserLoggedIn
    }

}