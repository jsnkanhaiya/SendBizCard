package com.sendbizcard.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.LoginErrorResponse
import com.sendbizcard.models.home.SavedCards
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
    suspend fun login(@Url url: String, @Body loginRequest: LoginRequestModel) : NetworkResponse<LoginResponseModel, LoginErrorResponse>

    @POST
    suspend fun updateUserProfile(@Url url: String, @Body updateProfileRequest : UpdateProfileRequest) : NetworkResponse<BaseResponseModel, LoginErrorResponse>


    @POST
    suspend fun register(@Url url: String, @Body registerRequestModel: RegisterRequestModel) : NetworkResponse<RegisterResponseModel, ErrorsListResponse>

    @POST
    suspend fun logoutUser(@Url url: String) : NetworkResponse<BaseResponseModel, LoginErrorResponse>


    @POST
    suspend fun forgotPassword(@Url url: String, @Body forgotPasswordRequestModel: ForgotPasswordRequestModel) : NetworkResponse<ForgotPasswordResponse, LoginErrorResponse>


    @POST
    suspend fun changePassword(@Url url: String, @Body changePasswordRequestModel: ChangePasswordRequestModel) : NetworkResponse<BaseResponseModel, LoginErrorResponse>


    @POST
    suspend fun verifyForgotPasswordOTP(@Url url: String, @Body registerRequestModel: VerifyForgotPasswordRequest) : NetworkResponse<BaseResponseModel, LoginErrorResponse>

    @POST
    suspend fun resendOTP(@Url url: String) : NetworkResponse<ForgotPasswordResponse, LoginErrorResponse>

    @POST
    suspend fun sendFeedBack(@Url url: String,@Body feedback:FeedBackRequestModel) : NetworkResponse<BaseResponseModel, LoginErrorResponse>


    @POST
    suspend fun verifyOtp(@Url url: String, @Body registerRequestModel: VerifyOtpRequest) : NetworkResponse<BaseResponseModel, ErrorsListResponse>

    @GET
    suspend fun getThemeList(@Url url: String) : NetworkResponse<ThemeResponse,ErrorsListResponse>

    @GET
    suspend fun getUserProfileData(@Url url: String) : NetworkResponse<UserProfileResponse,LoginErrorResponse>


    @POST
    suspend fun addCardRequest(@Url url: String, @Body addCardRequest: AddCardRequest) : NetworkResponse<BaseResponseModel,ErrorsListResponse>

    @POST
    suspend fun getCardUrl(@Url url: String, @Body viewCardRequest: ViewCardRequest) : NetworkResponse<ViewCardResponse, LoginErrorResponse>

    @POST
    suspend fun deleteCard(@Url url: String, @Body deleteCardRequest: DeleteCardRequest) : NetworkResponse<BaseResponseModel, LoginErrorResponse>


    @POST
    suspend fun getCardListSearch(@Url url: String, @Body cardListRequestModel: CardListRequestModel) : NetworkResponse<CardListResponseModel, LoginErrorResponse>

    @POST
    suspend fun getCardList(@Url url: String) : NetworkResponse<CardListResponseModel, LoginErrorResponse>



}