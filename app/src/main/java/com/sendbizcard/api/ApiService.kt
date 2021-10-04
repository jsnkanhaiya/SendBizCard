package com.sendbizcard.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.home.SavedCards
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
    fun register(@Url url: String, registerRequestModel: RegisterRequestModel) : NetworkResponse<SavedCards, ErrorsListResponse>

}