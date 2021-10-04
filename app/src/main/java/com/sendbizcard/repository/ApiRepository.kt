package com.sendbizcard.repository

import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.models.request.ChangePasswordRequestModel
import com.sendbizcard.models.request.ForgotPasswordRequestModel
import com.sendbizcard.models.request.LoginRequestModel
import com.sendbizcard.models.request.RegisterRequestModel
import com.sendbizcard.models.response.LoginResponseModel

interface ApiRepository {

    suspend fun login(loginRequest: LoginRequestModel): NetworkResponse<LoginResponseModel, ErrorsListResponse>

    suspend fun register(registerRequestModel: RegisterRequestModel) : NetworkResponse<RegisterRequestModel,ErrorsListResponse>
    suspend fun forgotPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel) : NetworkResponse<RegisterRequestModel,ErrorsListResponse>
    suspend fun changePassword(changePasswordRequestModel: ChangePasswordRequestModel) : NetworkResponse<RegisterRequestModel,ErrorsListResponse>
    suspend fun verifyForgotPasswordOTP(registerRequestModel: RegisterRequestModel) : NetworkResponse<RegisterRequestModel,ErrorsListResponse>
    suspend fun verifyOtp(registerRequestModel: RegisterRequestModel) : NetworkResponse<RegisterRequestModel,ErrorsListResponse>
    suspend fun logoutUser() : NetworkResponse<RegisterRequestModel,ErrorsListResponse>

}