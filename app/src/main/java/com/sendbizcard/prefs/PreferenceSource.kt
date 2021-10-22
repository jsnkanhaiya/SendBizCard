package com.sendbizcard.prefs

interface PreferenceSource {
    var isUserLoggedIn: Boolean
    var userId : String
    var userToken : String
    var userName : String
    var userEmail : String
    var userMobileNO : String
    var isFirstTimeUser : Boolean

}