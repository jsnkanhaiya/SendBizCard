package com.sendbizcard.repository

import com.sendbizcard.NetworkResponse
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.home.SavedCards

interface ApiRepository {

    suspend fun login() : NetworkResponse<SavedCards,ErrorsListResponse>

    suspend fun register() : NetworkResponse<SavedCards,ErrorsListResponse>

}