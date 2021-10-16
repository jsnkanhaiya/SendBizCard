package com.sendbizcard.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.api.ApiService
import com.sendbizcard.firebaseRemoteConfig.RemoteConfigImpl
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.LoginErrorResponse
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.models.request.*
import com.sendbizcard.models.response.BaseResponseModel
import com.sendbizcard.models.response.ForgotPasswordResponse
import com.sendbizcard.models.response.LoginResponseModel
import dagger.hilt.android.scopes.ViewModelScoped
import com.sendbizcard.models.response.RegisterResponseModel
import javax.inject.Inject

@ViewModelScoped
class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val remoteConfigImpl: RemoteConfigImpl
) : ApiRepository {

    override suspend fun login(loginRequest: LoginRequestModel): NetworkResponse<LoginResponseModel, LoginErrorResponse> {
        val url = remoteConfigImpl.getLoginURL()
        return apiService.login(url,loginRequest)
    }

    override suspend fun register(registerRequestModel: RegisterRequestModel): NetworkResponse<RegisterResponseModel, ErrorsListResponse> {
        val url = remoteConfigImpl.getRegisterURL()
        return apiService.register(url,registerRequestModel)
    }

    override suspend fun forgotPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel): NetworkResponse<ForgotPasswordResponse, LoginErrorResponse> {
        val url = remoteConfigImpl.getForgotPasswordURL()
        return apiService.forgotPassword(url,forgotPasswordRequestModel)
    }

    override suspend fun changePassword(changePasswordRequestModel: ChangePasswordRequestModel): NetworkResponse<BaseResponseModel, ErrorsListResponse> {
        val url = remoteConfigImpl.getChangePasswordURL()
        return apiService.changePassword(url,changePasswordRequestModel)
    }

    override suspend fun verifyForgotPasswordOTP(registerRequestModel: VerifyForgotPasswordRequest): NetworkResponse<BaseResponseModel, ErrorsListResponse> {
        val url = remoteConfigImpl.getVerifyOTPURL()
        return apiService.verifyForgotPasswordOTP(url,registerRequestModel)
    }

    override suspend fun verifyOtp(registerRequestModel: VerifyOtpRequest): NetworkResponse<BaseResponseModel, ErrorsListResponse> {
        val url = remoteConfigImpl.getVerifyOTPURL()
        return apiService.verifyOtp(url,registerRequestModel)
    }

    override suspend fun logoutUser(): NetworkResponse<SavedCards, ErrorsListResponse> {
        val url = remoteConfigImpl.getLogoutURL()
        return apiService.logoutUser(url)
    }

}