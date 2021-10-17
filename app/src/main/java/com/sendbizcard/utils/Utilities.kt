package com.sendbizcard.utils

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavOptions
import com.google.gson.JsonSyntaxException
import com.sendbizcard.R
import com.sendbizcard.dialog.ConfirmationDialogFragment
import com.sendbizcard.utils.AppConstants.IS_LOG_ON
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


 fun showErrorDialog(message: String, requireActivity: FragmentActivity, context:Context) {
    val confirmationDialogFragment = ConfirmationDialogFragment.Builder()
        .setAcceptButton(
            context?.resources?.getString(R.string.ok_confirmation)!!
        )
        .setTitle(
            context?.resources?.getString(R.string.error)!!
        )
        .setMessage(
            message
        )
        .setMessageTextColor(context.resources.getColor(R.color.textcolour))
        .build()
    confirmationDialogFragment.show(
        requireActivity.supportFragmentManager,
        ConfirmationDialogFragment.TAG
    )

    confirmationDialogFragment.setButtonClickListener(object :
        ConfirmationDialogFragment.OnButtonClickListener {
        override fun onAcceptClick() {
            confirmationDialogFragment.dismiss()
        }

        override fun onRejectClick() {
        }
    })

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