package com.sendbizcard.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.models.ErrorResponse
import com.sendbizcard.models.request.*
import com.sendbizcard.models.request.addCard.AddCardRequest
import com.sendbizcard.models.response.*
import com.sendbizcard.models.response.theme.ThemeResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {

    @POST
    suspend fun login(@Url url: String, @Body loginRequest: LoginRequestModel) : NetworkResponse<LoginResponseModel, ErrorResponse>

    @POST
    suspend fun updateUserProfile(@Url url: String, @Body updateProfileRequest : UpdateProfileRequest) : NetworkResponse<BaseResponseModel, ErrorResponse>

    @POST
    suspend fun register(@Url url: String, @Body registerRequestModel: RegisterRequestModel) : NetworkResponse<RegisterResponseModel, ErrorResponse>

    @POST
    suspend fun logoutUser(@Url url: String) : NetworkResponse<BaseResponseModel, ErrorResponse>

    @POST
    suspend fun forgotPassword(@Url url: String, @Body forgotPasswordRequestModel: ForgotPasswordRequestModel) : NetworkResponse<ForgotPasswordResponse, ErrorResponse>

    @POST
    suspend fun changePassword(@Url url: String, @Body changePasswordRequestModel: ChangePasswordRequestModel) : NetworkResponse<BaseResponseModel, ErrorResponse>

    @POST
    suspend fun verifyForgotPasswordOTP(@Url url: String, @Body registerRequestModel: VerifyForgotPasswordRequest) : NetworkResponse<BaseResponseModel, ErrorResponse>

    @POST
    suspend fun resendOTP(@Url url: String) : NetworkResponse<ForgotPasswordResponse, ErrorResponse>

    @POST
    suspend fun sendFeedBack(@Url url: String,@Body feedback:FeedBackRequestModel) : NetworkResponse<BaseResponseModel, ErrorResponse>

    @POST
    suspend fun verifyOtp(@Url url: String, @Body registerRequestModel: VerifyOtpRequest) : NetworkResponse<BaseResponseModel, ErrorResponse>

    @GET
    suspend fun getThemeList(@Url url: String) : NetworkResponse<ThemeResponse,ErrorResponse>

    @GET
    suspend fun getUserProfileData(@Url url: String) : NetworkResponse<UserProfileResponse,ErrorResponse>

    @POST
    suspend fun addCardRequest(@Url url: String, @Body addCardRequest: AddCardRequest) : NetworkResponse<BaseResponseModel,ErrorResponse>

    @POST
    suspend fun getCardUrl(@Url url: String, @Body viewCardRequest: ViewCardRequest) : NetworkResponse<ViewCardResponse, ErrorResponse>

    @POST
    suspend fun deleteCard(@Url url: String, @Body deleteCardRequest: DeleteCardRequest) : NetworkResponse<BaseResponseModel, LoginErrorResponse>


    @POST
    suspend fun getCardListSearch(@Url url: String, @Body cardListRequestModel: CardListRequestModel) : NetworkResponse<CardListResponseModel, ErrorResponse>

    @POST
    suspend fun getCardList(@Url url: String) : NetworkResponse<CardListResponseModel, ErrorResponse>

}