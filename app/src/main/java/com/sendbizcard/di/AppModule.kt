package com.sendbizcard.di

import com.sendbizcard.ui.home.adapter.SavedCardsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule  {

    @Provides
    @Singleton
    fun provideSavedCardsAdapter() : SavedCardsAdapter = SavedCardsAdapter()

}