package com.sendbizcard.utils

import com.chibatching.kotpref.KotprefModel

internal object AppPreferences : KotprefModel(){
    var isUserLoggedin by booleanPref()
}