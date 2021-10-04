package com.sendbizcard.prefs

interface PreferenceSource {
    var isUserLoggedIn: Boolean
    var userId : String
    var userToken : String
}