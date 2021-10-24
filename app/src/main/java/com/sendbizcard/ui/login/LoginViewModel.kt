package com.sendbizcard.ui.login

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.request.LoginRequestModel
import com.sendbizcard.models.response.LoginResponseModel
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.*
import com.sendbizcard.utils.AppConstants.ERROR_EMAIL
import com.sendbizcard.utils.AppConstants.ERROR_PASSWORD
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val preferenceSourceImpl: PreferenceSourceImpl
) : BaseViewModel() {

    var loginResponse = SingleLiveEvent<LoginResponseModel>()


    fun login(emailId: String, password: String) {
        val loginRequest = LoginRequestModel(emailId,password)
        jobList.add(
            launch {
                val result = withContext(Dispatchers.IO) {
                    apiRepositoryImpl.login(loginRequest)
                }
                when(result) {
                    is NetworkResponse.Success -> {
                        preferenceSourceImpl.isUserLoggedIn=true
                        preferenceSourceImpl.userToken= result.body.token.toString()
                        preferenceSourceImpl.userId= result.body.data?.id.toString()
                        preferenceSourceImpl.userMobileNO= result.body.data?.contact.toString()
                        preferenceSourceImpl.userName=result.body.data?.name.toString()
                        preferenceSourceImpl.userEmail= result.body.data?.email.toString()
                        loginResponse.value = result.body
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