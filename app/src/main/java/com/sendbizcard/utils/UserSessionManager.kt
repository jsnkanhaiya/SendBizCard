package com.sendbizcard.utils

import com.sendbizcard.models.request.addCard.ServicesItem

object UserSessionManager {

    var serviceList = ArrayList<ServicesItem>()

    fun addDataInServiceList(ourServicesList: MutableList<ServicesItem>) {
        serviceList.clear()
        serviceList.addAll(ourServicesList)
    }

    fun getDatFromServiceList() : ArrayList<ServicesItem> {
        return serviceList
    }

    fun clearServiceList() {
        serviceList.clear()
    }


}