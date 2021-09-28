package com.sendbizcard.repository

import com.sendbizcard.NetworkResponse
import com.sendbizcard.api.ApiService
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.prefs.PreferenceSource
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ApiRepository {

    override suspend fun login(): NetworkResponse<SavedCards, ErrorsListResponse> {
        return apiService.login("")
    }

    override suspend fun register(): NetworkResponse<SavedCards, ErrorsListResponse> {
        return apiService.register("")
    }

}