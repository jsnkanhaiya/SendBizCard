package com.sendbizcard.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.api.ApiService
import com.sendbizcard.firebaseRemoteConfig.RemoteConfigImpl
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.models.request.*
import com.sendbizcard.models.response.BaseResponseModel
import com.sendbizcard.models.response.ForgotPasswordResponse
import com.sendbizcard.models.response.LoginResponseModel
import com.sendbizcard.models.response.RegisterResponseModel
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val remoteConfigImpl: RemoteConfigImpl
) : ApiRepository {

    override suspend fun login(loginRequest: LoginRequestModel): NetworkResponse<LoginResponseModel, ErrorsListResponse> {
        //val url = "http://xapi.sendbusinesscard.com/api/login"
        val url = remoteConfigImpl.getLoginURL()
        return apiService.login(url,loginRequest)
    }

    override suspend fun register(registerRequestModel: RegisterRequestModel): NetworkResponse<RegisterResponseModel, ErrorsListResponse> {
        val url = "http://xapi.sendbusinesscard.com/api/register"
        return apiService.register(url,registerRequestModel)
    }

    override suspend fun forgotPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel): NetworkResponse<ForgotPasswordResponse, ErrorsListResponse> {
        val url = "http://xapi.sendbusinesscard.com/api/forgot/password"
        return apiService.forgotPassword(url,forgotPasswordRequestModel)
    }

    override suspend fun changePassword(changePasswordRequestModel: ChangePasswordRequestModel): NetworkResponse<BaseResponseModel, ErrorsListResponse> {
        val url = "http://xapi.sendbusinesscard.com/api/change/password"
        return apiService.changePassword(url,changePasswordRequestModel)
    }

    override suspend fun verifyForgotPasswordOTP(registerRequestModel: VerifyForgotPasswordRequest): NetworkResponse<BaseResponseModel, ErrorsListResponse> {
        val url = "http://xapi.sendbusinesscard.com/api/verify/forgotPassword/otp"
        return apiService.verifyForgotPasswordOTP(url,registerRequestModel)
    }

    override suspend fun verifyOtp(registerRequestModel: VerifyOtpRequest): NetworkResponse<BaseResponseModel, ErrorsListResponse> {
        val url = "http://xapi.sendbusinesscard.com/api/otp/verify"
        return apiService.verifyOtp(url,registerRequestModel)
    }

    override suspend fun logoutUser(): NetworkResponse<SavedCards, ErrorsListResponse> {
        val url = "http://xapi.sendbusinesscard.com/api/otp/verify"
        return apiService.logoutUser(url)
    }

}