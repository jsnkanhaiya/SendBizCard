package com.sendbizcard.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.api.ApiService
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.models.request.LoginRequestModel
import com.sendbizcard.models.request.RegisterRequestModel
import com.sendbizcard.models.response.LoginResponseModel
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ApiRepository {

    override suspend fun login(loginRequest: LoginRequestModel): NetworkResponse<LoginResponseModel, ErrorsListResponse> {
        val url = "http://xapi.sendbusinesscard.com/api/login"
        return apiService.login(url,loginRequest)
    }

    override suspend fun register(registerRequestModel: RegisterRequestModel): NetworkResponse<SavedCards, ErrorsListResponse> {
        val url = "http://xapi.sendbusinesscard.com/api/register"
        return apiService.register(url,registerRequestModel)
    }

    override suspend fun forgotPassword(registerRequestModel: RegisterRequestModel): NetworkResponse<SavedCards, ErrorsListResponse> {
        val url = "http://xapi.sendbusinesscard.com/api/forgot/password"
        return apiService.forgotPassword(url,registerRequestModel)
    }

    override suspend fun changePassword(registerRequestModel: RegisterRequestModel): NetworkResponse<SavedCards, ErrorsListResponse> {
        val url = "http://xapi.sendbusinesscard.com/api/change/password"
        return apiService.changePassword(url,registerRequestModel)
    }

    override suspend fun verifyForgotPasswordOTP(registerRequestModel: RegisterRequestModel): NetworkResponse<SavedCards, ErrorsListResponse> {
        val url = "http://xapi.sendbusinesscard.com/api/verify/forgotPassword/otp"
        return apiService.verifyForgotPasswordOTP(url,registerRequestModel)
    }

    override suspend fun verifyOtp(registerRequestModel: RegisterRequestModel): NetworkResponse<SavedCards, ErrorsListResponse> {
        val url = "http://xapi.sendbusinesscard.com/api/otp/verify"
        return apiService.verifyOtp(url,registerRequestModel)
    }

    override suspend fun logoutUser(): NetworkResponse<SavedCards, ErrorsListResponse> {
        val url = "http://xapi.sendbusinesscard.com/api/otp/verify"
        return apiService.logoutUser(url)
    }

}