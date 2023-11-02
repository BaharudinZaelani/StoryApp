package com.bahardev.storyapp.data.api

import com.google.gson.annotations.SerializedName

data class LoginResult(
    @field:SerializedName("tokenId")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String
)
