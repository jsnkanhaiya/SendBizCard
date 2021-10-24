package com.sendbizcard.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.models.ErrorResponse
import com.sendbizcard.models.request.*
import com.sendbizcard.models.request.addCard.AddCardRequest
import com.sendbizcard.models.response.*
import com.sendbizcard.models.response.theme.ThemeResponse

interface ApiRepository {
    suspend fun login(loginRequest: LoginRequestModel): NetworkResponse<LoginResponseModel, ErrorResponse>
    suspend fun updateUserProfile(updateProfileRequest: UpdateProfileRequest): NetworkResponse<BaseResponseModel, ErrorResponse>
    suspend fun register(registerRequestModel: RegisterRequestModel) : NetworkResponse<RegisterResponseModel,ErrorResponse>
    suspend fun forgotPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel) : NetworkResponse<ForgotPasswordResponse,ErrorResponse>
    suspend fun changePassword(changePasswordRequestModel: ChangePasswordRequestModel) : NetworkResponse<BaseResponseModel,ErrorResponse>
    suspend fun verifyForgotPasswordOTP(registerRequestModel: VerifyForgotPasswordRequest) : NetworkResponse<BaseResponseModel,ErrorResponse>
    suspend fun verifyOtp(registerRequestModel: VerifyOtpRequest) : NetworkResponse<BaseResponseModel,ErrorResponse>
    suspend fun resendOTP() : NetworkResponse<ForgotPasswordResponse,ErrorResponse>
    suspend fun logoutUser() : NetworkResponse<BaseResponseModel,ErrorResponse>
    suspend fun getThemeList() : NetworkResponse<ThemeResponse,ErrorResponse>
    suspend fun getUserProfileData() : NetworkResponse<UserProfileResponse,ErrorResponse>
    suspend fun sendFeedBack(feedback:FeedBackRequestModel) : NetworkResponse<BaseResponseModel,ErrorResponse>
    suspend fun addCardRequest(addCardRequest: AddCardRequest) : NetworkResponse<BaseResponseModel,ErrorResponse>
    suspend fun getCardUrl(viewCardRequest: ViewCardRequest) : NetworkResponse<ViewCardResponse,ErrorResponse>
    suspend fun deleteCard(deleteCardRequest: DeleteCardRequest) : NetworkResponse<BaseResponseModel,ErrorResponse>
    suspend fun getCardListSearch(cardListRequest: CardListRequestModel) : NetworkResponse<CardListResponseModel,ErrorResponse>
    suspend fun getCardList() : NetworkResponse<CardListResponseModel,ErrorResponse>
}