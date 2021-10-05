package com.sendbizcard.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.sendbizcard.ui.home.adapter.SavedCardsAdapter
import com.sendbizcard.prefs.PreferenceSource
import com.sendbizcard.prefs.PreferenceSourceImpl
import com.sendbizcard.utils.AppConstants.APPLICATION_PREFERENCE_NAME
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

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context) : SharedPreferences {
        return context.getSharedPreferences(APPLICATION_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesPreferencesSourceImpl(preferences: SharedPreferences): PreferenceSourceImpl {
        return PreferenceSourceImpl(preferences)
    }

    @Provides
    @Singleton
    fun provideFireBaseConfig(): FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireBaseSettings(): FirebaseRemoteConfigSettings {
        return FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()

    }


}