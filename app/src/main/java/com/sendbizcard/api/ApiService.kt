package com.sendbizcard.api

import com.sendbizcard.NetworkResponse
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.models.request.LoginRequestModel
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {

    @POST
    fun login(@Url url: String, @Body reqObj: LoginRequestModel) : NetworkResponse<SavedCards,ErrorsListResponse>

    @POST
    fun register(@Url url: String) : NetworkResponse<SavedCards,ErrorsListResponse>

}