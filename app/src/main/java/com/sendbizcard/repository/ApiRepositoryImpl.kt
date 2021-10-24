package com.sendbizcard.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.api.ApiService
import com.sendbizcard.firebaseRemoteConfig.RemoteConfigImpl
import com.sendbizcard.models.ErrorResponse
import com.sendbizcard.models.request.*
import com.sendbizcard.models.request.addCard.AddCardRequest
import com.sendbizcard.models.response.*
import dagger.hilt.android.scopes.ViewModelScoped
import com.sendbizcard.models.response.theme.ThemeResponse
import javax.inject.Inject

@ViewModelScoped
class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val remoteConfigImpl: RemoteConfigImpl
) : ApiRepository {

    override suspend fun login(loginRequest: LoginRequestModel): NetworkResponse<LoginResponseModel, ErrorResponse> {
        val url = remoteConfigImpl.getLoginURL()
        return apiService.login(url,loginRequest)
    }

    override suspend fun updateUserProfile(updateProfileRequest : UpdateProfileRequest): NetworkResponse<BaseResponseModel, ErrorResponse> {
        val url = remoteConfigImpl.getUpdateUserDetailsURL()
        return apiService.updateUserProfile(url,updateProfileRequest)
    }

    override suspend fun register(registerRequestModel: RegisterRequestModel): NetworkResponse<RegisterResponseModel, ErrorResponse> {
        val url = remoteConfigImpl.getRegisterURL()
        return apiService.register(url,registerRequestModel)
    }

    override suspend fun forgotPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel): NetworkResponse<ForgotPasswordResponse, ErrorResponse> {
        val url = remoteConfigImpl.getForgotPasswordURL()
        return apiService.forgotPassword(url,forgotPasswordRequestModel)
    }

    override suspend fun changePassword(changePasswordRequestModel: ChangePasswordRequestModel): NetworkResponse<BaseResponseModel, ErrorResponse> {
        val url = remoteConfigImpl.getChangePasswordURL()
        return apiService.changePassword(url,changePasswordRequestModel)
    }

    override suspend fun verifyForgotPasswordOTP(registerRequestModel: VerifyForgotPasswordRequest): NetworkResponse<BaseResponseModel, ErrorResponse> {
        val url = remoteConfigImpl.getForgotVerifyOTPURL()
        return apiService.verifyForgotPasswordOTP(url,registerRequestModel)
    }

    override suspend fun resendOTP(): NetworkResponse<ForgotPasswordResponse, ErrorResponse> {
        val url = remoteConfigImpl.getResendOTPURL()
        return apiService.resendOTP(url)
    }

    override suspend fun verifyOtp(registerRequestModel: VerifyOtpRequest): NetworkResponse<BaseResponseModel, ErrorResponse> {
        val url = remoteConfigImpl.getVerifyOTPURL()
        return apiService.verifyOtp(url,registerRequestModel)
    }

    override suspend fun logoutUser(): NetworkResponse<BaseResponseModel, ErrorResponse> {
        val url = remoteConfigImpl.getLogoutURL()
        return apiService.logoutUser(url)
    }

    override suspend fun getThemeList(): NetworkResponse<ThemeResponse, ErrorResponse> {
        val url = remoteConfigImpl.getThemeListURL()
        return apiService.getThemeList(url)
    }

    override suspend fun getUserProfileData(): NetworkResponse<UserProfileResponse, ErrorResponse> {
        val url = remoteConfigImpl.getUserDetailsURL()
        return apiService.getUserProfileData(url)
    }


    override suspend fun sendFeedBack(feedback:FeedBackRequestModel): NetworkResponse<BaseResponseModel, ErrorResponse> {
        val url = remoteConfigImpl.getFeedbackURL()
        return apiService.sendFeedBack(url,feedback)
    }

    override suspend fun addCardRequest(addCardRequest: AddCardRequest): NetworkResponse<BaseResponseModel, ErrorResponse> {
        val url = remoteConfigImpl.getAddCardURL()
        return apiService.addCardRequest(url,addCardRequest)
    }

    override suspend fun getCardUrl(viewCardRequest: ViewCardRequest): NetworkResponse<ViewCardResponse, ErrorResponse> {
        val url = remoteConfigImpl.getCardShareURL()
        return apiService.getCardUrl(url,viewCardRequest)
    }

    override suspend fun deleteCard( deleteCardRequest:DeleteCardRequest): NetworkResponse<BaseResponseModel, ErrorResponse> {
        val url = remoteConfigImpl.getDeleteCardURL()
        return apiService.deleteCard(url,deleteCardRequest)
    }

    override suspend fun getCardListSearch(cardListRequest: CardListRequestModel): NetworkResponse<CardListResponseModel, ErrorResponse> {
        val url = remoteConfigImpl.getCardShareURL()
        return apiService.getCardListSearch(url,cardListRequest)
    }

    override suspend fun getCardList(): NetworkResponse<CardListResponseModel, ErrorResponse> {
        val url = remoteConfigImpl.getCardListURL()
        return apiService.getCardList(url)
    }

}