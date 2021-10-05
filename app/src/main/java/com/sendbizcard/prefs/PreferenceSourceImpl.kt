package com.sendbizcard.prefs

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceSourceImpl @Inject constructor(private val preferences: SharedPreferences) : PreferenceSource {

    companion object {
        const val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"
        const val USER_ID = "USER_ID"
        const val USER_TOKEN = "USER_TOKEN"
    }

    override var isUserLoggedIn: Boolean
        get() = preferences.getBoolean(IS_USER_LOGGED_IN,false) ?: false
        set(value) {
            preferences.edit().putBoolean(IS_USER_LOGGED_IN,value).apply()
        }

    override var userId: String
        get() = preferences.getString(USER_ID,"") ?: ""
        set(value) {
            preferences.edit().putString(USER_ID,value).apply()
        }

    override var userToken: String
        get() = preferences.getString(USER_TOKEN,"") ?: ""
        set(value) {
            preferences.edit().putString(USER_TOKEN,value).apply()
        }
}