package com.sendbizcard.utils

import android.text.TextUtils
import android.util.Patterns


object ValidationUtils {


  fun isFieldEmpty(value: String): Boolean {
    return value.trim().isEmpty()
  }

  fun isUserNameEmpty(userName: String): Boolean {
    return userName.isNullOrEmpty()
  }

  fun isValidOtp(otp: String): Boolean {
    return otp.length==6
  }

  fun isValidMobileNo(mobileNo: String): Boolean {
    return mobileNo.length==10
  }

  fun isRequiredPasswordLengthForChangePassword(password: String): Boolean {
    return password.length >= AppConstants.MINIMUM_PASSWORD_LENGTH
  }

  fun isRequiredPasswordLengthForLogin(password: String): Boolean {
    return password.length >= AppConstants.MINIMUM_PASSWORD_LENGTH-2
  }

  fun isBothPasswordMatch(password: String, confirmPassword: String): Boolean {
    return password == confirmPassword
  }

   fun isValidEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
  }

}