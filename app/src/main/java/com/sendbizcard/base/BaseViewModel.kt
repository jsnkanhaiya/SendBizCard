package com.sendbizcard.base

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.sendbizcard.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() , CoroutineScope , LifecycleObserver {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    val jobList = mutableListOf<Job>()
    //val showServerError = SingleLiveEvent<ServerError>()
    val showNetworkError = SingleLiveEvent<String>()
    val showUnknownError = SingleLiveEvent<String>()

    override fun onCleared() {
        super.onCleared()
        cancelJobs()
    }

    private fun cancelJobs() {
        if (!jobList.isNullOrEmpty()) {
            for (i in jobList) {
                i.cancel()
            }
            jobList.clear()
        }
    }

}