package com.sendbizcard.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.LoginErrorResponse
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.models.request.*
import com.sendbizcard.models.response.BaseResponseModel
import com.sendbizcard.models.response.ForgotPasswordResponse
import com.sendbizcard.models.response.LoginResponseModel
import com.sendbizcard.models.response.RegisterResponseModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {

    @POST
    suspend fun login(@Url url: String, @Body loginRequest: LoginRequestModel) : NetworkResponse<LoginResponseModel, LoginErrorResponse>

    @POST
    fun register(@Url url: String, @Body registerRequestModel: RegisterRequestModel) : NetworkResponse<RegisterResponseModel, ErrorsListResponse>

    @POST
    fun logoutUser(@Url url: String) : NetworkResponse<SavedCards, ErrorsListResponse>


    @POST
    fun forgotPassword(@Url url: String, @Body forgotPasswordRequestModel: ForgotPasswordRequestModel) : NetworkResponse<ForgotPasswordResponse, ErrorsListResponse>


    @POST
    fun changePassword(@Url url: String, @Body changePasswordRequestModel: ChangePasswordRequestModel) : NetworkResponse<BaseResponseModel, ErrorsListResponse>


    @POST
    fun verifyForgotPasswordOTP(@Url url: String, @Body registerRequestModel: VerifyForgotPasswordRequest) : NetworkResponse<BaseResponseModel, ErrorsListResponse>


    @POST
    fun verifyOtp(@Url url: String, @Body registerRequestModel: VerifyOtpRequest) : NetworkResponse<BaseResponseModel, ErrorsListResponse>

    @GET
    suspend fun getThemeList(
        @Url url: String
    )

}