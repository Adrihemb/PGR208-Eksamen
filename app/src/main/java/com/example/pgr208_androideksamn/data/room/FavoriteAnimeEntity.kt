package com.example.pgr208_androideksamn.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_anime")
data class FavoriteAnimeEntity(
    @PrimaryKey val malId: Int,
    val title: String,
    val imageUrl: String?,
    val type: String?,      // f.eks. "TV", "Movie"
    val score: Double?      // kan være null fra API
)
