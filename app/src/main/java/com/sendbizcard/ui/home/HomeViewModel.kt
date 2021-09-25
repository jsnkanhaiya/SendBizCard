package com.sendbizcard.ui.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sendbizcard.models.home.SavedCards
import com.sendbizcard.repository.ApiRepository
import com.sendbizcard.repository.ApiRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiRepositoryImpl: ApiRepositoryImpl
) : ViewModel(), LifecycleObserver {

    val listOfSavedCards : MutableLiveData<ArrayList<SavedCards>> by lazy { MutableLiveData<ArrayList<SavedCards>>() }

    fun getListOfSavedCards() {

        val listOfItems = ArrayList<SavedCards>()
        listOfItems.add(SavedCards("Card 1","Designation"))
        listOfItems.add(SavedCards("Card 2","Designation"))
        listOfItems.add(SavedCards("Card 3","Designation"))
        listOfItems.add(SavedCards("Card 4","Designation"))
        listOfItems.add(SavedCards("Card 5","Designation"))
        listOfItems.add(SavedCards("Card 6","Designation"))
        listOfItems.add(SavedCards("Card 7","Designation"))
        listOfItems.add(SavedCards("Card 8","Designation"))
        listOfItems.add(SavedCards("Card 9","Designation"))
        listOfItems.add(SavedCards("Card 10","Designation"))

        listOfSavedCards.value = listOfItems

    }

}