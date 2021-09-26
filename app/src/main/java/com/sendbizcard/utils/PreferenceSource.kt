package com.sendbizcard.utils

interface PreferenceSource {
    var isUserLoggedIn: Boolean
    var userId : String
}