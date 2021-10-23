package com.sendbizcard.utils

import android.content.Context
import android.util.Log
import androidx.navigation.NavOptions
import com.google.gson.JsonSyntaxException
import com.sendbizcard.R
import com.sendbizcard.utils.AppConstants.IS_LOG_ON
import com.sendbizcard.models.LoginErrorResponse
import java.io.EOFException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException

import android.content.Intent
import android.graphics.Bitmap
import android.util.Base64
import com.sendbizcard.BuildConfig
import java.io.ByteArrayOutputStream


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

fun getDefaultNavigationAnimation() = NavOptions.Builder().apply {
    this.setEnterAnim(R.anim.slide_in_right).setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left).setPopExitAnim(R.anim.slide_out_right)
}.build()

fun printLog(tag: String, message: String) {
//    Log.e(tag, message)
    when (IS_LOG_ON) {
        true -> Log.e(tag, message)
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

fun shareApp(context: Context){
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(
        Intent.EXTRA_TEXT,
        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
    )
    sendIntent.type = "text/plain"
    context.startActivity(sendIntent)
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

fun convertBitmapToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}