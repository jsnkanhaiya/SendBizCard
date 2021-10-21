package com.sendbizcard.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.api.ApiService
import com.sendbizcard.firebaseRemoteConfig.RemoteConfigImpl
import com.sendbizcard.models.ErrorsListResponse
import com.sendbizcard.models.LoginErrorResponse
import com.sendbizcard.models.home.SavedCards
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

    override suspend fun login(loginRequest: LoginRequestModel): NetworkResponse<LoginResponseModel, LoginErrorResponse> {
        val url = remoteConfigImpl.getLoginURL()
        return apiService.login(url,loginRequest)
    }

    override suspend fun updateUserProfile(updateProfileRequest : UpdateProfileRequest): NetworkResponse<BaseResponseModel, LoginErrorResponse> {
        val url = remoteConfigImpl.getUpdateUserDetailsURL()
        return apiService.updateUserProfile(url,updateProfileRequest)
    }

    override suspend fun register(registerRequestModel: RegisterRequestModel): NetworkResponse<RegisterResponseModel, ErrorsListResponse> {
        val url = remoteConfigImpl.getRegisterURL()
        return apiService.register(url,registerRequestModel)
    }

    override suspend fun forgotPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel): NetworkResponse<ForgotPasswordResponse, LoginErrorResponse> {
        val url = remoteConfigImpl.getForgotPasswordURL()
        return apiService.forgotPassword(url,forgotPasswordRequestModel)
    }

    override suspend fun changePassword(changePasswordRequestModel: ChangePasswordRequestModel): NetworkResponse<BaseResponseModel, LoginErrorResponse> {
        val url = remoteConfigImpl.getChangePasswordURL()
        return apiService.changePassword(url,changePasswordRequestModel)
    }

    override suspend fun verifyForgotPasswordOTP(registerRequestModel: VerifyForgotPasswordRequest): NetworkResponse<BaseResponseModel, LoginErrorResponse> {
        val url = remoteConfigImpl.getForgotVerifyOTPURL()
        return apiService.verifyForgotPasswordOTP(url,registerRequestModel)
    }

    override suspend fun resendOTP(): NetworkResponse<ForgotPasswordResponse, LoginErrorResponse> {
        val url = remoteConfigImpl.getResendOTPURL()
        return apiService.resendOTP(url)
    }

    override suspend fun verifyOtp(registerRequestModel: VerifyOtpRequest): NetworkResponse<BaseResponseModel, ErrorsListResponse> {
        val url = remoteConfigImpl.getVerifyOTPURL()
        return apiService.verifyOtp(url,registerRequestModel)
    }

    override suspend fun logoutUser(): NetworkResponse<SavedCards, ErrorsListResponse> {
        val url = remoteConfigImpl.getLogoutURL()
        return apiService.logoutUser(url)
    }

    override suspend fun getThemeList(): NetworkResponse<ThemeResponse, ErrorsListResponse> {
        val url = remoteConfigImpl.getThemeListURL()
        return apiService.getThemeList(url)
    }

    override suspend fun getUserProfileData(): NetworkResponse<UserProfileResponse, LoginErrorResponse> {
        val url = remoteConfigImpl.getUserDetailsURL()
        return apiService.getUserProfileData(url)
    }


    override suspend fun sendFeedBack(feedback:FeedBackRequestModel): NetworkResponse<BaseResponseModel, LoginErrorResponse> {
        val url = remoteConfigImpl.getFeedbackURL()
        return apiService.sendFeedBack(url,feedback)
    }

    override suspend fun addCardRequest(addCardRequest: AddCardRequest): NetworkResponse<ThemeResponse, ErrorsListResponse> {
        val url = remoteConfigImpl.getAddCardURL()
        return apiService.addCardRequest(url,addCardRequest)
    }

    override suspend fun getCradUrl(viewCardRequest: ViewCardRequest): NetworkResponse<ViewCardResponse, LoginErrorResponse> {
        val url = remoteConfigImpl.getCardShareURL()
        return apiService.getCradUrl(url,viewCardRequest)
    }

    override suspend fun getCardListSearch(cardListRequest: CardListRequestModel): NetworkResponse<CardListResponseModel, LoginErrorResponse> {
        val url = remoteConfigImpl.getCardShareURL()
        return apiService.getCardListSearch(url,cardListRequest)
    }

    override suspend fun getCardList(): NetworkResponse<CardListResponseModel, LoginErrorResponse> {
        val url = remoteConfigImpl.getCardShareURL()
        return apiService.getCardList(url)
    }

}