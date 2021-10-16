package com.sendbizcard.utils

import com.google.gson.JsonSyntaxException
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.LoginErrorResponse
import com.sendbizcard.models.ServerError
import java.io.EOFException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException

fun decodeNetworkError(throwable: Throwable): String {
    return when (throwable) {
        is EOFException -> {
            "No response from server."
        }
        is SocketTimeoutException -> {
            "Server connection timeout."
        }
        is OutOfMemoryError -> {
            "Not enough memory."
        }
        is SSLHandshakeException -> {
            "Invalid SSL Certificate."
        }
        else -> {
            "You're currently offline! Please check your internet connection and try again."
        }
    }
}

fun decodeUnknownError(throwable: Throwable): String {
    return when (throwable) {
        is JsonSyntaxException -> {
            "We\\'re not able to process your request\\n right now. Please try again later."
        }
        else -> {
            "An unexpected error occurred \nSorry for the inconvenience."
        }
    }
}

fun decodeServerError(errorResponse: LoginErrorResponse?): String {
    return errorResponse?.message?.username?.getOrNull(0) ?: "Something Went Wrong"
}

fun getHttpStatus(httpCode: Int): HttpStatus {
    var httpStatus = HttpStatus.DEFAULT
    for (i in HttpStatus.values()) {
        if (httpCode == i.code) {
            httpStatus = i
            break
        }
    }
    return httpStatus
}