package com.sendbizcard.ui.introScreen

import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroScreenViewModel @Inject constructor(
    private val preferenceSourceImpl: PreferenceSourceImpl
) : BaseViewModel() {

    fun setFirstTimeUser(isFirstTimeUser:Boolean){
        preferenceSourceImpl.isFirstTimeUser=isFirstTimeUser
    }

}