package com.sendbizcard.utils

import android.content.Context

class PreferenceSourceImpl(private val context: Context) : PreferenceSource {

    override var isUserLoggedIn: Boolean
        get() = AppPreferences.isUserLoggedin
        set(value) {
            AppPreferences.isUserLoggedin = value
        }

    override var userId: String
        get() = AppPreferences.userId
        set(value) {
            AppPreferences.userId = value
        }
}