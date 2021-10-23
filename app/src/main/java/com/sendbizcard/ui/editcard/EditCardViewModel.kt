package com.sendbizcard.ui.editcard

import androidx.lifecycle.LifecycleObserver
import com.sendbizcard.base.BaseViewModel
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.repository.ApiRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditCardViewModel  @Inject constructor(
private val apiRepositoryImpl: ApiRepositoryImpl,
private val preferenceSourceImpl: PreferenceSourceImpl

) : BaseViewModel(), LifecycleObserver {
}