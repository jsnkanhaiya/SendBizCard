package com.sendbizcard.ui.otp

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.request.RegisterRequestModel
import com.sendbizcard.models.request.VerifyForgotPasswordRequest
import com.sendbizcard.models.request.VerifyOtpRequest
import com.sendbizcard.models.response.BaseResponseModel
import com.sendbizcard.models.response.ForgotPasswordResponse
import com.sendbizcard.models.response.RegisterResponseModel
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VerifyOtpViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val preferenceSourceImpl: PreferenceSourceImpl

) : BaseViewModel(), LifecycleObserver {

    var otpResponseModel = SingleLiveEvent<BaseResponseModel>()
    var otpResponseReSendModel = SingleLiveEvent<ForgotPasswordResponse>()


    fun isValidOtpData(otp: String): Boolean {
        return when {
            otp.isBlank() -> {
                false
            }
            else -> ValidationUtils.isValidOtp(otp)
        }
    }

    fun isValidForgotOtpData(otp: String): Boolean {
        return when {
            otp.isBlank() -> {
                false
            }
            else -> ValidationUtils.isValidForgotOtp(otp)
        }
    }

    fun verifyForGotOtp(
        otp: String,
        email: String
    ) {
        val verifyOtpRequest =
            VerifyForgotPasswordRequest(otp,email)
        jobList.add(launch {
            val result = withContext(Dispatchers.IO) {
                apiRepositoryImpl.verifyForgotPasswordOTP(verifyOtpRequest)
            }
            when (result) {
                is NetworkResponse.Success -> {
                    otpResponseModel.value=result.body
                    preferenceSourceImpl.isUserLoggedIn= true
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


    fun resendOTP() {

        jobList.add(launch {
            val result = withContext(Dispatchers.IO) {
                apiRepositoryImpl.resendOTP()
            }
            when (result) {
                is NetworkResponse.Success -> {
                    otpResponseReSendModel.value=result.body
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

    fun verifyOtp(
        otp: String
    ) {
        val verifyOtpRequest =
            VerifyOtpRequest(otp)
        jobList.add(launch {
            val result = withContext(Dispatchers.IO) {
                apiRepositoryImpl.verifyOtp(verifyOtpRequest)
            }
            when (result) {
                is NetworkResponse.Success -> {
                    otpResponseModel.value=result.body
                    preferenceSourceImpl.isUserLoggedIn= true
                }

                is NetworkResponse.ServerError -> {
                   // showServerError.value = decodeServerError(result.body)
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
}