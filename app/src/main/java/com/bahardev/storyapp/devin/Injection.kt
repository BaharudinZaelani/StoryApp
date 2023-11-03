package com.bahardev.storyapp.devin

import android.content.Context
import com.bahardev.storyapp.data.StoryRepository
import com.bahardev.storyapp.data.UserRepository
import com.bahardev.storyapp.data.api.ApiConfig
import com.bahardev.storyapp.data.pref.UserPreference
import com.bahardev.storyapp.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
    fun storyProvideRepository(token: String): StoryRepository {
        val apiService = ApiConfig().getApiServiceAuth(token)
        return StoryRepository(apiService)
    }
}