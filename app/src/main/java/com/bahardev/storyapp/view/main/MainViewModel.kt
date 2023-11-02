package com.bahardev.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bahardev.storyapp.data.UserRepository
import com.bahardev.storyapp.data.pref.UserModel

class MainViewModel(private val repository: UserRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    suspend fun setSession(user: UserModel) {
        repository.saveSession(user)
    }
    suspend fun deleteSession() {
        repository.logout()
    }
}