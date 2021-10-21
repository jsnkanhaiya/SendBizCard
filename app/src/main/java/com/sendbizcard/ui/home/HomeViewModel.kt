package com.sendbizcard.ui.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.models.request.addCard.AddCardRequest
import com.sendbizcard.models.response.BaseResponseModel
import com.sendbizcard.repository.ApiRepository
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.UserSessionManager
import com.sendbizcard.utils.decodeNetworkError
import com.sendbizcard.utils.decodeServerError
import com.sendbizcard.utils.decodeUnknownError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl
) : BaseViewModel() {

    var logoutResponse = MutableLiveData<BaseResponseModel>()

    fun addCardRequest(mName: String, mDesignation: String, mMobileNumber: String, mEmailId: String, mWebsite: String, mLocation: String) {
        val addCardRequest = AddCardRequest().apply {
            themeId = "3"
            themeColor = "#748484"
            name = mName
            userImg = ""
            designation = mDesignation
            contactPrefix = "+91"
            contactNo = mMobileNumber
            email = mEmailId
            website = mWebsite
            location = mLocation
            companyLogo = ""
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


}