package com.sendbizcard.prefs

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceSourceImpl @Inject constructor(private val preferences: SharedPreferences) : PreferenceSource {

    companion object {
        const val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"
        const val USER_ID = "USER_ID"
        const val USER_TOKEN = "USER_TOKEN"
        const val USER_NAME = "USER_NAME"
        const val PROFILE_IMAGE = "PROFILE_IMAGE"
        const val USER_EMAIL = "USER_EMAIL"
        const val USER_THEME_ID = "USER_THEME_ID"
        const val USER_MOBILE_NO = "USER_MOBILE_NO"
        const val IS_FIRST_TIME_USER = "IS_FIRST_TIME_USER"
    }

    override var isUserLoggedIn: Boolean
        get() = preferences.getBoolean(IS_USER_LOGGED_IN,false)
        set(value) {
            preferences.edit().putBoolean(IS_USER_LOGGED_IN,value).apply()
        }

    override var isFirstTimeUser: Boolean
        get() = preferences.getBoolean(IS_FIRST_TIME_USER,true)
        set(value) {
            preferences.edit().putBoolean(IS_FIRST_TIME_USER,value).apply()
        }

    override var userId: String
        get() = preferences.getString(USER_ID,"") ?: ""
        set(value) {
            preferences.edit().putString(USER_ID,value).apply()
        }

    override var themeID: String
        get() = preferences.getString(USER_THEME_ID,"") ?: ""
        set(value) {
            preferences.edit().putString(USER_THEME_ID,value).apply()
        }

    override var userToken: String
        get() = preferences.getString(USER_TOKEN,"") ?: ""
        set(value) {
            preferences.edit().putString(USER_TOKEN,value).apply()
        }

    override var userName: String
        get() = preferences.getString(USER_NAME,"") ?: ""
        set(value) {
            preferences.edit().putString(USER_NAME,value).apply()
        }

    override var profileImage: String
        get() = preferences.getString(PROFILE_IMAGE,"") ?: ""
        set(value) {
            preferences.edit().putString(PROFILE_IMAGE,value).apply()
        }


    override var userEmail: String
        get() = preferences.getString(USER_EMAIL,"") ?: ""
        set(value) {
            preferences.edit().putString(USER_EMAIL,value).apply()
        }


    override var userMobileNO: String
        get() = preferences.getString(USER_MOBILE_NO,"") ?: ""
        set(value) {
            preferences.edit().putString(USER_MOBILE_NO,value).apply()
        }

    fun clearData(){
        preferences.edit().putString(USER_TOKEN,"").apply()
        preferences.edit().putBoolean(IS_USER_LOGGED_IN,false).apply()
        preferences.edit().putString(USER_MOBILE_NO,"").apply()
        preferences.edit().putString(USER_EMAIL,"").apply()
        preferences.edit().putString(USER_ID,"").apply()

    }

}