package com.sendbizcard.ui.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.models.request.addCard.AddCardRequest
import com.sendbizcard.repository.ApiRepository
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl
) : BaseViewModel() {

    fun addCardRequest() {
        val addCardRequest = AddCardRequest().apply {
            themeId = "3"
            themeColor = "#748484"
            name = "Brijesh Dubey"
            userImg = ""
            designation = "Software Engineer"
            contactPrefix = "+91"
            contactNo = "9594116610"
            email = "brijesh@gmail.com"
            website = "https://brijesh.com"
            location = "2220, 22nd floor, A&O building, near fish market, borivali east, mumbai 400066"
            companyLogo = ""
            companyName = "Brijesh"
            services = UserSessionManager.getDatFromServiceList()
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

                    }

                    is NetworkResponse.NetworkError -> {

                    }

                    is NetworkResponse.UnknownError -> {

                    }
                }
            }
        )


    }

}