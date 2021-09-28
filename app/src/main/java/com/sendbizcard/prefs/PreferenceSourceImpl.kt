package com.sendbizcard.prefs

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceSourceImpl @Inject constructor(private val preferences: SharedPreferences) : PreferenceSource {

    private object PreferencesKeys {
        const val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"
        const val USER_ID = "USER_ID"
    }

    override var isUserLoggedIn: Boolean
        get() = preferences.getBoolean(PreferencesKeys.IS_USER_LOGGED_IN,false) ?: false
        set(value) {
            preferences.edit().putBoolean(PreferencesKeys.IS_USER_LOGGED_IN,value).apply()
        }

    override var userId: String
        get() = preferences.getString(PreferencesKeys.USER_ID,"") ?: ""
        set(value) {
            preferences.edit().putString(PreferencesKeys.USER_ID,value).apply()
        }
}