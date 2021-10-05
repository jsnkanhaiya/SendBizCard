package com.sendbizcard.firebaseRemoteConfig

interface RemoteConfigProvider  {

    fun getBaseURL() : String
    fun getLoginURL() : String

}