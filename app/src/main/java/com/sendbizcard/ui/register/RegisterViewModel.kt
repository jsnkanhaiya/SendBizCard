package com.sendbizcard.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sendbizcard.utils.ValidationUtils
import com.sendbizcard.utils.ValidationUtils.isValidEmail
import com.sendbizcard.utils.ValidationUtils.isValidMobileNo

class RegisterViewModel : ViewModel() {

    var strName = MutableLiveData<String>()
    var strMobileNo = MutableLiveData<String>()
    var strEmailId = MutableLiveData<String>()
    var strPassword = MutableLiveData<String>()
    var strConfirmPassword = MutableLiveData<String>()

    fun registerUser() {

    }

    fun isValidAllValue(): Boolean {
        if (strName.value.isNullOrBlank()) {
            return false
        } else if (strMobileNo.value.isNullOrBlank()) {
            return false
        } else if (!isValidMobileNo(strMobileNo.value!!)) {
            return false
        } else if (strEmailId.value.isNullOrBlank()) {
            return false
        } else if (!isValidEmail(strEmailId.value!!)) {
            return false
        } else if (strPassword.value.isNullOrBlank()) {
            return false
        } else if (strConfirmPassword.value.isNullOrBlank()) {
            return false
        } else if (!ValidationUtils.isBothPasswordMatch(
                strPassword.value!!,
                strConfirmPassword.value!!
            )
        ) {
            return false
        } else {
            return true
        }

    }
}