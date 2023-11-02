package com.bahardev.storyapp.data.api

import com.google.gson.annotations.SerializedName

data class FileUploadResponse(
    @field:SerializedName("message")
    val message: String
)
