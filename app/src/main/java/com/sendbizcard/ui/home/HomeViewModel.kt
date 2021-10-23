package com.sendbizcard.ui.home

import androidx.lifecycle.MutableLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.request.addCard.AddCardRequest
import com.sendbizcard.models.response.BaseResponseModel
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val preferenceSourceImpl: PreferenceSourceImpl
) : BaseViewModel() {

    var logoutResponse = MutableLiveData<BaseResponseModel>()
    var saveCradResponse = SingleLiveEvent<BaseResponseModel>()

    fun addCardRequest(
        mName: String,
        mDesignation: String,
        mMobileNumber: String,
        mEmailId: String,
        mWebsite: String,
        mLocation: String,
        mUserImageBase64String: String,
        mCompanyLogoBase64String: String,
        backgroundColour: String
    ) {
        val addCardRequest = AddCardRequest().apply {
            themeId = preferenceSourceImpl.themeID
            themeColor = backgroundColour
            name = mName
            userImg = mUserImageBase64String
            designation = mDesignation
            contactPrefix = "+91"
            contactNo = mMobileNumber
            email = mEmailId
            website = mWebsite
            location = mLocation
            companyLogo = mCompanyLogoBase64String
            companyName = "ABC"
            services = UserSessionManager.getDataFromServiceList()
            socialLinks = null
        }
        jobList.add(
            launch {
                val result = withContext(Dispatchers.IO){
                    apiRepositoryImpl.addCardRequest(addCardRequest)
                }
                when (result) {
                    is NetworkResponse.Success -> {
                        saveCradResponse.value=result.body
                    }

                    is NetworkResponse.ServerError -> {
                       // showServerError.value = decodeServerError(result.body)
                    }

                    is NetworkResponse.NetworkError -> {
                        showNetworkError.value = decodeNetworkError(result.error)
                    }

                    is NetworkResponse.UnknownError -> {
                        showUnknownError.value = decodeUnknownError(result.error)
                    }
                }
            }
        )


    }



    fun logOutUser(){
        jobList.add(
            launch {
                val result = withContext(Dispatchers.IO){
                    apiRepositoryImpl.logoutUser()
                }
                when (result) {
                    is NetworkResponse.Success -> {
                        logoutResponse.value= result.body
                    }

                    is NetworkResponse.ServerError -> {
                        showServerError.value = decodeServerError(result.body)
                    }

                    is NetworkResponse.NetworkError -> {
                        showNetworkError.value = decodeNetworkError(result.error)
                    }

                    is NetworkResponse.UnknownError -> {
                        showUnknownError.value = decodeUnknownError(result.error)
                    }
                }
            }
        )


    }

    fun clearAllData(){
        preferenceSourceImpl.clearData()
    }


}