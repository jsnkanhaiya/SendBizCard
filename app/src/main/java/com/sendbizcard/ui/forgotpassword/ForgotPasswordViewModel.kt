package com.sendbizcard.ui.forgotpassword

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.request.ChangePasswordRequestModel
import com.sendbizcard.models.request.ForgotPasswordRequestModel
import com.sendbizcard.models.request.RegisterRequestModel
import com.sendbizcard.models.response.BaseResponseModel
import com.sendbizcard.models.response.ForgotPasswordResponse
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.*
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

    var forgotPasswordResponse = SingleLiveEvent<ForgotPasswordResponse>()
    var changePasswordResponse = SingleLiveEvent<BaseResponseModel>()

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
                    forgotPasswordResponse.value = result.body
                }

                is NetworkResponse.ServerError -> {
                    showServerError.value = decodeServerError(result.body)
                }

                is NetworkResponse.NetworkError -> {
                    showNetworkError.value = decodeNetworkError(result.error)
                }

                is NetworkResponse.UnknownError -> {
                    showUnknownError.value = decodeUnknownError(result.error)
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
                    changePasswordResponse.value = result.body
                }

                is NetworkResponse.ServerError -> {
                    showServerError.value = decodeServerError(result.body)
                }

                is NetworkResponse.NetworkError -> {
                    showNetworkError.value = decodeNetworkError(result.error)
                }

                is NetworkResponse.UnknownError -> {
                    showUnknownError.value = decodeUnknownError(result.error)
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
            else -> ValidationUtils.isValidEmailChar(emailId)
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
            /*ValidationUtils.isRequiredPasswordLengthForLogin(password) -> {
                false
            }
            ValidationUtils.isRequiredPasswordLengthForLogin(confPassword) -> {
                false
            }*/
            /*ValidationUtils.isRequiredPasswordLengthForChangePassword(password) -> {
                false
            }*/
            else ->ValidationUtils.isBothPasswordMatch(password,confPassword)
        }

    }

}