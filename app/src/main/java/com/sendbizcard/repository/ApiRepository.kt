package com.sendbizcard.repository

import com.sendbizcard.NetworkResponse
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.models.request.LoginRequestModel

interface ApiRepository {

    suspend fun login(reqObj: LoginRequestModel): NetworkResponse<SavedCards,ErrorsListResponse>

    suspend fun register() : NetworkResponse<SavedCards,ErrorsListResponse>

}