package com.sendbizcard.utils

import java.util.regex.Pattern

object AppConstants  {
    const val APPLICATION_PREFERENCE_NAME = "send_biz_card_pref"
    const val IMAGE_BASE_URL = "https://xapi.sendbusinesscard.com/storage/"
    const val ERROR_EMAIL = "Enter proper email Id"
    const val ERROR_PASSWORD = "Enter proper password"
    const val ERROR_NAME = "Enter proper name"
    const val ERROR_MOBILE = "Enter proper mobile number"
    const val MINIMUM_PASSWORD_LENGTH = 6
    const val IS_LOG_ON = true
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
}