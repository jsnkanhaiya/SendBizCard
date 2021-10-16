package com.sendbizcard.firebaseRemoteConfig

interface RemoteConfigProvider  {

    fun getBaseURL(): String
    fun getLoginURL(): String
    fun getRegisterURL(): String
    fun getForgotPasswordURL(): String
    fun getChangePasswordURL(): String
    fun getVerifyOTPURL(): String
    fun getResendOTPURL(): String
    fun getAddCardURL(): String
    fun getFeedbackURL(): String
    fun getLogoutURL(): String
    fun getThemeListURL(): String

}