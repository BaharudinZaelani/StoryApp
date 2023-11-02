package com.bahardev.storyapp.data

import com.bahardev.storyapp.data.pref.UserModel
import com.bahardev.storyapp.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow
import kotlin.concurrent.Volatile

class UserRepository private constructor(
    private val userPreference: UserPreference
){
    companion object{
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance( userPreference: UserPreference ): UserRepository =
            instance ?: synchronized(this) {
                instance?: UserRepository(userPreference)
            }.also { instance = it }
    }

    // Function
    suspend fun saveSession(user: UserModel){
        userPreference.saveSession(user)
    }
    fun getSession(): Flow<UserModel>{
        return userPreference.getSession()
    }
    suspend fun logout() {
        userPreference.logout()
    }
}