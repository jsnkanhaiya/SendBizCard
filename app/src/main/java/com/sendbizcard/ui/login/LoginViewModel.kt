package com.sendbizcard.ui.login

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.request.LoginRequestModel
import com.sendbizcard.models.response.LoginResponseModel
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val preferenceSourceImpl: PreferenceSourceImpl

) : BaseViewModel(), LifecycleObserver {

    var loginReponse = MutableLiveData<LoginResponseModel>()
   // var loginReponse = MutableLiveData<LoginResponseModel>()

    fun login(emailId: String, password: String) {
        val loginRequest = LoginRequestModel(emailId,password)
        jobList.add(
            launch {
                val result = withContext(Dispatchers.IO) {
                    apiRepositoryImpl.login(loginRequest)
                }
                when(result) {
                    is NetworkResponse.Success -> {
                        loginReponse = result
                        preferenceSourceImpl.userToken= loginReponse.value?.token.toString()
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



    fun isValidLoginData(emailId: String, password: String): Boolean {
        return when {
            emailId.isBlank() -> {
                false
            }
            password.isBlank() -> {
                false
            }
            else -> ValidationUtils.isRequiredPasswordLengthForChangePassword(password)
        }

    }
}