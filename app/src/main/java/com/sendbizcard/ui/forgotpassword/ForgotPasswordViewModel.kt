package com.sendbizcard.ui.forgotpassword

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.request.ChangePasswordRequestModel
import com.sendbizcard.models.request.ForgotPasswordRequestModel
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
class ForgotPasswordViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val preferenceSourceImpl: PreferenceSourceImpl

) : BaseViewModel(), LifecycleObserver {

    var forgotPasswordReponse = MutableLiveData<Boolean>()
    var changePasswordResponse = MutableLiveData<Boolean>()

    fun forgotPasswordUser(
        emailId: String,
    ) {
        val forgotPasswordRequest =
            ForgotPasswordRequestModel(emailId)
        jobList.add(launch {
            val result = withContext(Dispatchers.IO) {
                apiRepositoryImpl.forgotPassword(forgotPasswordRequest)
            }
            when (result) {
                is NetworkResponse.Success -> {
                    forgotPasswordReponse.value = true
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

    fun changePasswordUser(
        otp: String,
        password: String,
        confPassword: String
    ) {
        val changePasswordRequestModel =
            ChangePasswordRequestModel(password,otp,confPassword)
        jobList.add(launch {
            val result = withContext(Dispatchers.IO) {
                apiRepositoryImpl.changePassword(changePasswordRequestModel)
            }
            when (result) {
                is NetworkResponse.Success -> {
                    changePasswordResponse.value = true
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


    fun isValidData(

        emailId: String,

    ): Boolean {
        return when {

            emailId.isBlank() -> {
                false
            }
            else -> ValidationUtils.isValidEmail(emailId)
        }

    }

    fun isValidChangePasswordData(
        otp: String,
        password: String,
        confPassword: String
    ): Boolean {
        return when {

            otp.isBlank() -> {
                false
            }
            ValidationUtils.isValidOtp(otp) -> {
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
            ValidationUtils.isRequiredPasswordLengthForChangePassword(password) -> {
                false
            }
            else ->ValidationUtils.isBothPasswordMatch(password,confPassword)
        }

    }

}