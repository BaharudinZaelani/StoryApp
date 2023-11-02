package com.bahardev.storyapp.devin

import android.content.Context
import com.bahardev.storyapp.data.UserRepository
import com.bahardev.storyapp.data.api.ApiConfig
import com.bahardev.storyapp.data.pref.UserPreference
import com.bahardev.storyapp.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
//    fun provideNewsRepository(context: Context): NewsRepository {
//        val apiService = ApiConfig().getApiService()
//        val database = NewsDatabase.getInstance(context)
//        val dao = database.newsDao()
//        val appExecutors = AppExecutors()
//        return NewsRepository.getInstance(apiService, dao, appExecutors)
//    }
}