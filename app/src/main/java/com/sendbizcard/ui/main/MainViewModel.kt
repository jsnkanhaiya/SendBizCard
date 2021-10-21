package com.sendbizcard.ui.main

import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.prefs.PreferenceSourceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferenceSourceImpl: PreferenceSourceImpl
) : BaseViewModel() {

    fun checkIfFirstTimeUser() = preferenceSourceImpl.isFirstTimeUser

}