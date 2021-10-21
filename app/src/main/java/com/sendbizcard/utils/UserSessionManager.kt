package com.sendbizcard.utils

import com.sendbizcard.models.request.addCard.ServicesItem
import com.sendbizcard.models.request.addCard.SocialLinksItem

object UserSessionManager {

    var serviceList = ArrayList<ServicesItem>()
    var socialMediaList = ArrayList<SocialLinksItem>()

    fun addDataInServiceList(ourServicesList: MutableList<ServicesItem>) {
        serviceList.clear()
        serviceList.addAll(ourServicesList)
    }

    fun addDataInSocialMediaList(socialMediaLinksList: ArrayList<SocialLinksItem>){
        socialMediaList.clear()
        socialMediaList.addAll(socialMediaLinksList)
    }


    fun getDataFromServiceList() : ArrayList<ServicesItem> {
        return serviceList
    }

    fun getDataFromSocialMediaList() : ArrayList<SocialLinksItem>{
        return socialMediaList
    }

    fun clearList() {
        serviceList.clear()
        socialMediaList.clear()
    }


}