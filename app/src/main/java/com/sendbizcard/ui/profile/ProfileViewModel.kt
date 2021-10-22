package com.sendbizcard.ui.profile

import androidx.lifecycle.MutableLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.request.UpdateProfileRequest
import com.sendbizcard.models.response.BaseResponseModel
import com.sendbizcard.models.response.UserProfileResponse
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.decodeNetworkError
import com.sendbizcard.utils.decodeServerError
import com.sendbizcard.utils.decodeUnknownError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val preferenceSourceImpl: PreferenceSourceImpl
) : BaseViewModel()  {

    var userProfileResponse = MutableLiveData<UserProfileResponse>()
    var updateUserProfileResponse = MutableLiveData<BaseResponseModel>()

    fun getUserProfileData() {

        jobList.add(
            launch {
                val result = withContext(Dispatchers.IO) {
                    apiRepositoryImpl.getUserProfileData()
                }
                when(result) {
                    is NetworkResponse.Success -> {
                        //loginReponse = result
                        userProfileResponse.value=result.body
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

    fun updateUserData(
        name: String,
        mobile: String,
        email: String,
        website: String,
        designation: String,
        userImageBase64String: String
    ) {
        val updateUserProfileRequestModel = UpdateProfileRequest(website,name,email,mobile,designation,userImageBase64String)
        jobList.add(
            launch {
                val result = withContext(Dispatchers.IO) {
                    apiRepositoryImpl.updateUserProfile(updateUserProfileRequestModel)
                }
                when(result) {
                    is NetworkResponse.Success -> {
                        updateUserProfileResponse.value=result.body
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



    fun isValidUserProfileData(
        name: String,
        mobileNo: String,
        emailId: String,
        website: String,
        designation: String
    ): Boolean {
        return when {
            name.isNullOrEmpty() -> {
                false
            }
            mobileNo.isNullOrEmpty() -> {
                false
            }
            emailId.isNullOrEmpty() -> {
                false
            }
            website.isNullOrEmpty() -> {
                false
            }
            designation.isNullOrEmpty() -> {
                false
            }
            else -> true
        }

    }




}