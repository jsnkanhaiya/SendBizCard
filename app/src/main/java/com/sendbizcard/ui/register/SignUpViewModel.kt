package com.sendbizcard.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    var strName = MutableLiveData<String>()
    var strMobileNo = MutableLiveData<String>()
    var strEmailId = MutableLiveData<String>()
    var strPassword = MutableLiveData<String>()
    var strConfirmPassword = MutableLiveData<String>()



    fun isValidAllValue(){

    }
}