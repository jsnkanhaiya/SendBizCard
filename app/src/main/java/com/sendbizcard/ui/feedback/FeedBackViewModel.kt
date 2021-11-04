package com.sendbizcard.ui.feedback

import androidx.lifecycle.MutableLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.request.FeedBackRequestModel
import com.sendbizcard.models.response.BaseResponseModel
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.SingleLiveEvent
import com.sendbizcard.utils.decodeNetworkError
import com.sendbizcard.utils.decodeServerError
import com.sendbizcard.utils.decodeUnknownError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FeedBackViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val preferenceSourceImpl: PreferenceSourceImpl
) : BaseViewModel() {

    var feedbackResponse = SingleLiveEvent<BaseResponseModel>()


    fun sendFeedBAck(strFeedBAck: String) {
        val feedBackRequestModel = FeedBackRequestModel(
            preferenceSourceImpl.userName,
            preferenceSourceImpl.userMobileNO,
            strFeedBAck,
            preferenceSourceImpl.userEmail
        )
        jobList.add(
            launch {
                val result = withContext(Dispatchers.IO) {
                    apiRepositoryImpl.sendFeedBack(feedBackRequestModel)
                }
                when (result) {
                    is NetworkResponse.Success -> {
                        feedbackResponse.value = result.body
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