package com.sendbizcard.ui.login

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl
) : ViewModel(), LifecycleObserver {

    var strEmailId = MutableLiveData<String>()


    fun registerUser() {

    }

    fun isValidEmailID(): Boolean {
        if (strEmailId.value.isNullOrBlank()) {
            return false
        } else if (!ValidationUtils.isValidEmail(strEmailId.value!!)) {
            return false
        } else {
            return true
        }

    }
}