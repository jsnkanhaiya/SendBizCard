package com.sendbizcard.ui.sharecard

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.request.ViewCardRequest
import com.sendbizcard.models.response.ViewCardResponse
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
class ViewCardViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val preferenceSourceImpl: PreferenceSourceImpl
) : BaseViewModel() {

    var viewCardResponse = SingleLiveEvent<ViewCardResponse>()


    fun getCardURL(id: String, themeId: String) {
        val viewCardRequest = ViewCardRequest(id,themeId)
        jobList.add(
            launch {
                val result = withContext(Dispatchers.IO) {
                    apiRepositoryImpl.getCradUrl(viewCardRequest)
                }
                when(result) {
                    is NetworkResponse.Success -> {
                        //loginReponse = result
                        viewCardResponse.value = result.body
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