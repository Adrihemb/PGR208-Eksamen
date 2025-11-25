package com.example.pgr208_androideksamn.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pgr208_androideksamn.data.anime.Anime

@TypeConverters(Converters::class)
@Database(
    entities = [
        Anime::class,
        FavoriteAnimeEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun favoriteAnimeDao(): FavoriteAnimeDao
}
