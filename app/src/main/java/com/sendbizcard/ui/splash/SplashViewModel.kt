package com.sendbizcard.ui.splash

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    private val configSettings: FirebaseRemoteConfigSettings,
    private val preferenceSourceImpl: PreferenceSourceImpl
) : BaseViewModel() {


    init {
        call()
    }

    fun call() {
        jobList.add(
            launch {
                withContext(Dispatchers.IO) {
                    fetchAndActivateConfigParameters()
                }
            }
        )
    }


    private suspend fun fetchAndActivateConfigParameters() {
        remoteConfig.setConfigSettingsAsync(configSettings).await()
        remoteConfig.fetchAndActivate().await()
    }


    fun checkIfUserIsLoggedIn() : Boolean {
        return preferenceSourceImpl.isUserLoggedIn
    }



}