package com.sendbizcard.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.LoginErrorResponse
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.models.request.*
import com.sendbizcard.models.response.BaseResponseModel
import com.sendbizcard.models.response.ForgotPasswordResponse
import com.sendbizcard.models.response.LoginResponseModel
import com.sendbizcard.models.response.RegisterResponseModel

interface ApiRepository {
    suspend fun login(loginRequest: LoginRequestModel): NetworkResponse<LoginResponseModel, LoginErrorResponse>
    suspend fun register(registerRequestModel: RegisterRequestModel) : NetworkResponse<RegisterResponseModel,ErrorsListResponse>
    suspend fun forgotPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel) : NetworkResponse<ForgotPasswordResponse,ErrorsListResponse>
    suspend fun changePassword(changePasswordRequestModel: ChangePasswordRequestModel) : NetworkResponse<BaseResponseModel,ErrorsListResponse>
    suspend fun verifyForgotPasswordOTP(registerRequestModel: VerifyForgotPasswordRequest) : NetworkResponse<BaseResponseModel,ErrorsListResponse>
    suspend fun verifyOtp(registerRequestModel: VerifyOtpRequest) : NetworkResponse<BaseResponseModel,ErrorsListResponse>
    suspend fun logoutUser() : NetworkResponse<SavedCards,ErrorsListResponse>
}