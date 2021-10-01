package com.sendbizcard.repository

import com.sendbizcard.NetworkResponse
import com.sendbizcard.api.ApiService
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.models.request.LoginRequestModel
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ApiRepository {

    override suspend fun login(reqObj: LoginRequestModel): NetworkResponse<SavedCards, ErrorsListResponse> {
        return apiService.login("",reqObj)
    }

    override suspend fun register(): NetworkResponse<SavedCards, ErrorsListResponse> {
        return apiService.register("")
    }

}