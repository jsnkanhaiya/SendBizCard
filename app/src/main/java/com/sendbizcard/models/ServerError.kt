package com.sendbizcard.models

import androidx.annotation.Keep
import com.sendbizcard.utils.HttpStatus


data class ServerError(
    val httpStatus: Int,
    val errorMessage: String,
)