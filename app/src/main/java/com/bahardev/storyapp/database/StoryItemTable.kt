package com.bahardev.storyapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story")
data class StoryItemTable(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val photo: String,
    val createdAt: String,
    val lat: Double? = null,
    val lon: Double? = null
)
