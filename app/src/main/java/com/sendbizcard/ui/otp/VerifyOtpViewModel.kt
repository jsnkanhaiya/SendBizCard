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
import com.sendbizcard.models.response.RegisterResponseModel
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.ValidationUtils
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

    var otpResponseModel = MutableLiveData<BaseResponseModel>()


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
            VerifyForgotPasswordRequest(otp)
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

                }

                is NetworkResponse.NetworkError -> {

                }

                is NetworkResponse.UnknownError -> {

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

                }

                is NetworkResponse.NetworkError -> {

                }

                is NetworkResponse.UnknownError -> {

                }
            }
        })

    }
}