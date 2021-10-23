package com.sendbizcard.ui.theme

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponse
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.models.response.theme.ListThemeItem
import com.sendbizcard.models.response.theme.ThemeResponse
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import com.sendbizcard.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl,
    private val preferenceSourceImpl: PreferenceSourceImpl
) : BaseViewModel() {


    fun saveThemeId(themeId:String){
        preferenceSourceImpl.themeID= themeId
    }

    val themeList : SingleLiveEvent<List<ListThemeItem>> by lazy { SingleLiveEvent() }

    fun getThemeList() {
        jobList.add(
            launch {
                val result = withContext(Dispatchers.IO) {
                    apiRepositoryImpl.getThemeList()
                }
                when (result) {
                    is NetworkResponse.Success -> {
                        result.body.data?.listTheme?.let {
                            themeList.value = it
                        }
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