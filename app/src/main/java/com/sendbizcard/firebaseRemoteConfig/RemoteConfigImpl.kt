package com.sendbizcard.firebaseRemoteConfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject

class RemoteConfigImpl @Inject constructor(private val firebaseRemoteConfig: FirebaseRemoteConfig) :  RemoteConfigProvider {

    companion object {
        const val BASE_URL = "base_url"
        const val LOGIN_URL = "login_url"
    }

    override fun getBaseURL(): String {
        return firebaseRemoteConfig.getString(BASE_URL)
    }

    override fun getLoginURL(): String {
        return getBaseURL() + firebaseRemoteConfig.getString(LOGIN_URL)
    }
}