package com.sendbizcard.utils

import android.content.Context
import android.util.Log
import androidx.navigation.NavOptions
import com.google.gson.JsonSyntaxException
import com.sendbizcard.R
import com.sendbizcard.utils.AppConstants.IS_LOG_ON
import java.io.EOFException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException

import android.content.Intent
import android.graphics.Bitmap
import android.util.Base64
import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.BuildConfig
import com.sendbizcard.models.ErrorResponse
import com.sendbizcard.models.ServerError
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

fun decodeServerError(errorResponse: ErrorResponse?): ServerError {
    var errorMessage: String?= null
    val code = errorResponse?.status ?: -1
    errorMessage = errorResponse?.message?.errors?.getOrNull(0)
    if (errorMessage != null) {
        return ServerError(code,errorMessage)
    } else {
        val contactErrorMessage = errorResponse?.message?.contact?.getOrNull(0) ?: ""
        val emailErrorMessage = errorResponse?.message?.email?.getOrNull(0) ?: ""
        val usernameErrorMessage = errorResponse?.message?.username?.getOrNull(0) ?: ""
        val passwordErrorMessage = errorResponse?.message?.password?.getOrNull(0) ?: ""
        val idErrorMessage = errorResponse?.message?.id?.getOrNull(0) ?: ""
        val otpErrorMessage = errorResponse?.message?.otp?.getOrNull(0) ?: ""
        errorMessage = if (contactErrorMessage.isNotEmpty() && emailErrorMessage.isNotEmpty()){
            contactErrorMessage + "\n" + emailErrorMessage
        } else if (contactErrorMessage.isNotEmpty()){
            contactErrorMessage
        }else if (usernameErrorMessage.isNotEmpty()){
            usernameErrorMessage
        } else if (passwordErrorMessage.isNotEmpty()){
            passwordErrorMessage
        } else if (passwordErrorMessage.isNotEmpty()){
            passwordErrorMessage
        } else if (emailErrorMessage.isNotEmpty()){
            emailErrorMessage
        } else if (idErrorMessage.isNotEmpty()){
            idErrorMessage
        } else if (otpErrorMessage.isNotEmpty()){
            otpErrorMessage
        }   else {
            "Something Went Wrong"
        }
        return ServerError(code,errorMessage)
    }
}

fun shareApp(context: Context,text:String ){
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(
        Intent.EXTRA_TEXT,
        text
    )
    sendIntent.type = "text/plain"
    context.startActivity(sendIntent)
}


fun convertBitmapToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}