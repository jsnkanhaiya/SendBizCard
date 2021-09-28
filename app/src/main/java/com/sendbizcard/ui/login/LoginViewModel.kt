package com.sendbizcard.ui.login

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sendbizcard.NetworkResponse
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl
) : BaseViewModel(), LifecycleObserver {

    var strEmailId = MutableLiveData<String>()

    fun login() {
        jobList.add(
            launch {
                val result = withContext(Dispatchers.IO) {
                    apiRepositoryImpl.login()
                }
                when(result) {
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



    fun isValidEmailID(): Boolean {
        if (strEmailId.value.isNullOrBlank()) {
            return false
        } else if (!ValidationUtils.isValidEmail(strEmailId.value!!)) {
            return false
        } else {
            return true
        }

    }
}