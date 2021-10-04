package com.sendbizcard.ui.register

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.request.RegisterRequestModel
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val preferenceSourceImpl: PreferenceSourceImpl

) : BaseViewModel(), LifecycleObserver {

    var registerReponse = MutableLiveData<Boolean>()


    fun registerUser(
        name: String,
        mobileNo: String,
        emailId: String,
        password: String,
        confPassword: String
    ) {
        val registerRequestModel =
            RegisterRequestModel(password, mobileNo, name, emailId, confPassword)
        jobList.add(launch {
            val result = withContext(Dispatchers.IO) {
                apiRepositoryImpl.register(registerRequestModel)
            }
            when (result) {
                is NetworkResponse.Success -> {
                    // registerReponse = result
                }

                is NetworkResponse.ServerError -> {

                }

                is NetworkResponse.NetworkError -> {

                }

                is NetworkResponse.UnknownError -> {

                }
            }
        })

    }


    fun isValidRegisterData(
        name: String,
        mobileNo: String,
        emailId: String,
        password: String,
        confPassword: String
    ): Boolean {
        return when {
            name.isBlank() -> {
                false
            }
            mobileNo.isBlank() -> {
                false
            }
            emailId.isBlank() -> {
                false
            }
            confPassword.isBlank() -> {
                false
            }
            ValidationUtils.isRequiredPasswordLengthForLogin(password) -> {
                false
            }
            ValidationUtils.isRequiredPasswordLengthForLogin(confPassword) -> {
                false
            }
            else -> ValidationUtils.isRequiredPasswordLengthForChangePassword(password)
        }

    }

}