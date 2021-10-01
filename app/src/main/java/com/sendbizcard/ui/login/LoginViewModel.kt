package com.sendbizcard.ui.login

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.sendbizcard.NetworkResponse
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.request.LoginRequestModel
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl
) : BaseViewModel(), LifecycleObserver {

    var strEmailId = MutableLiveData<String>()
    var strPassword = MutableLiveData<String>()

    fun login() {
        jobList.add(
            launch {
                val result = withContext(Dispatchers.IO) {
                    var reqObj = LoginRequestModel(strEmailId.value,strPassword.value)
                    apiRepositoryImpl.login(reqObj)
                }
                when(result) {
                    is NetworkResponse.Success -> {

                    }

                    is NetworkResponse.ServerError -> {

                    }

                    is NetworkResponse.NetworkError -> {

                    }

                    is NetworkResponse.UnknownError -> {

                    }
                }
            }
        )
    }



    fun isValidLoginData(): Boolean {
        if (strEmailId.value.isNullOrBlank()) {
            return false
        } else if (!ValidationUtils.isValidEmail(strEmailId.value!!)) {
            return false
        }else if (strPassword.value.isNullOrBlank()) {
            return false
        } else if (!ValidationUtils.isRequiredPasswordLengthForChangePassword(strPassword.value!!)) {
            return false
        } else {
            return true
        }

    }
}