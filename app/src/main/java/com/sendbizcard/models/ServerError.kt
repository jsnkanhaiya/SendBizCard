package com.sendbizcard.models

import androidx.annotation.Keep

@Keep
data class ServerError(
    var code: Int,
    var errorMessage: String
)
