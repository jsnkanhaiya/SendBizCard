package com.sendbizcard.ui.feedback

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FeedBackViewModel : ViewModel(){

    var strFeedBack = MutableLiveData<String>()

    fun isValidAllValue(): Boolean {
        if (!strFeedBack.value.isNullOrBlank()){
            return false
        }else{
            return true
        }
    }

    fun sendFeedBack() {
    }
}