package com.example.pgr208_androideksamn.data.anime

import androidx.room.Entity
import androidx.room.PrimaryKey

//Lokal lagring
@Entity(tableName = "anime_ideas")
data class AnimeIdea(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val genre: String
)