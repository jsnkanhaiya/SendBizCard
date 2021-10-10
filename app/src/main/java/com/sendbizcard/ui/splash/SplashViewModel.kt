package com.sendbizcard.ui.splash

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
    private val firebaseRemoteConfigSettings: FirebaseRemoteConfigSettings,
    private val preferenceSourceImpl: PreferenceSourceImpl
) : BaseViewModel() {

    val isConfigParsed: SingleLiveEvent<Boolean> by lazy { SingleLiveEvent() }

    fun fetchAndActivateConfigParameters() {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRemoteConfig.setConfigSettingsAsync(firebaseRemoteConfigSettings)
            firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isConfigParsed.postValue(true)
                } else {
                    isConfigParsed.postValue(false)
                }
            }
            firebaseRemoteConfig.fetchAndActivate().addOnFailureListener {
                isConfigParsed.postValue(false)
            }
        }
    }


    fun checkIfUserIsLoggedIn() : Boolean {
        return preferenceSourceImpl.isUserLoggedIn
    }



}