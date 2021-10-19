package com.sendbizcard.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.LoginErrorResponse
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.models.request.*
import com.sendbizcard.models.request.addCard.AddCardRequest
import com.sendbizcard.models.response.*
import com.sendbizcard.models.response.theme.ThemeResponse

interface ApiRepository {
    suspend fun login(loginRequest: LoginRequestModel): NetworkResponse<LoginResponseModel, LoginErrorResponse>
    suspend fun updateUserProfile(updateProfileRequest: UpdateProfileRequest): NetworkResponse<BaseResponseModel, LoginErrorResponse>
    suspend fun register(registerRequestModel: RegisterRequestModel) : NetworkResponse<RegisterResponseModel,ErrorsListResponse>
    suspend fun forgotPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel) : NetworkResponse<ForgotPasswordResponse,LoginErrorResponse>
    suspend fun changePassword(changePasswordRequestModel: ChangePasswordRequestModel) : NetworkResponse<BaseResponseModel,LoginErrorResponse>
    suspend fun verifyForgotPasswordOTP(registerRequestModel: VerifyForgotPasswordRequest) : NetworkResponse<BaseResponseModel,LoginErrorResponse>
    suspend fun verifyOtp(registerRequestModel: VerifyOtpRequest) : NetworkResponse<BaseResponseModel,ErrorsListResponse>
    suspend fun resendOTP() : NetworkResponse<ForgotPasswordResponse,LoginErrorResponse>
    suspend fun logoutUser() : NetworkResponse<SavedCards,ErrorsListResponse>
    suspend fun getThemeList() : NetworkResponse<ThemeResponse,ErrorsListResponse>
    suspend fun getUserProfileData() : NetworkResponse<UserProfileResponse,LoginErrorResponse>
    suspend fun sendFeedBack(feedback:FeedBackRequestModel) : NetworkResponse<BaseResponseModel,LoginErrorResponse>
    suspend fun addCardRequest(addCardRequest: AddCardRequest) : NetworkResponse<ThemeResponse,ErrorsListResponse>
}