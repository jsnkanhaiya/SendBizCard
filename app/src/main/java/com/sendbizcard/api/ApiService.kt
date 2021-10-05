package com.sendbizcard.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.models.request.ChangePasswordRequestModel
import com.sendbizcard.models.request.ForgotPasswordRequestModel
import com.sendbizcard.models.request.LoginRequestModel
import com.sendbizcard.models.request.RegisterRequestModel
import com.sendbizcard.models.response.LoginResponseModel
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {

    @POST
    suspend fun login(@Url url: String, @Body loginRequest: LoginRequestModel) : NetworkResponse<LoginResponseModel,ErrorsListResponse>

    @POST
    fun register(@Url url: String, @Body registerRequestModel: RegisterRequestModel) : NetworkResponse<SavedCards, ErrorsListResponse>

    @POST
    fun logoutUser(@Url url: String) : NetworkResponse<SavedCards, ErrorsListResponse>


    @POST
    fun forgotPassword(@Url url: String, @Body forgotPasswordRequestModel: ForgotPasswordRequestModel) : NetworkResponse<SavedCards, ErrorsListResponse>


    @POST
    fun changePassword(@Url url: String, @Body changePasswordRequestModel: ChangePasswordRequestModel) : NetworkResponse<SavedCards, ErrorsListResponse>


    @POST
    fun verifyForgotPasswordOTP(@Url url: String, @Body registerRequestModel: RegisterRequestModel) : NetworkResponse<SavedCards, ErrorsListResponse>


    @POST
    fun verifyOtp(@Url url: String, @Body registerRequestModel: RegisterRequestModel) : NetworkResponse<SavedCards, ErrorsListResponse>


}