package com.sendbizcard.ui.otp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sendbizcard.utils.ValidationUtils

class VerifyOtpViewModel : ViewModel() {

    var strOtp = MutableLiveData<String>()


    fun isValidOtp(): Boolean {
        if (strOtp.value?.let { ValidationUtils.isValidOtp(it) } != true){
            return false
        }else{
            return true
        }
    }

    fun verifyOtp() {
    }
}