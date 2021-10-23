package com.sendbizcard.ui.cardlist

import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.request.CardListRequestModel
import com.sendbizcard.models.request.LoginRequestModel
import com.sendbizcard.models.response.CardDetailsItem
import com.sendbizcard.models.response.CardListResponseModel
import com.sendbizcard.models.response.LoginResponseModel
import com.sendbizcard.models.response.theme.ListThemeItem
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
class CardListViewModel@Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val preferenceSourceImpl: PreferenceSourceImpl
) : BaseViewModel() {

    val cardListLiveData : SingleLiveEvent<List<CardDetailsItem>> by lazy { SingleLiveEvent() }

    fun getThemeId():String{
        return preferenceSourceImpl.themeID
    }

    fun getCardList() {
     //   val cardListRequestModel = CardListRequestModel("",0,10,null,"")
        jobList.add(
            launch {
                val result = withContext(Dispatchers.IO) {
                    apiRepositoryImpl.getCardList()
                }
                when(result) {
                    is NetworkResponse.Success -> {
                        result.body.data?.cardDetails?.let { cardList ->
                            cardListLiveData.value = cardList
                        }

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