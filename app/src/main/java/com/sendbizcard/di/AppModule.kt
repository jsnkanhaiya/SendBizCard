package com.sendbizcard.di

import android.content.Context
import com.sendbizcard.ui.home.adapter.SavedCardsAdapter
import com.sendbizcard.utils.PreferenceSource
import com.sendbizcard.utils.PreferenceSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule  {

    @Provides
    @Singleton
    fun provideSavedCardsAdapter() : SavedCardsAdapter = SavedCardsAdapter()

    @Singleton
    @Provides
    fun providesPreferencesSource(@ApplicationContext context: Context): PreferenceSource =
        PreferenceSourceImpl(context)

}