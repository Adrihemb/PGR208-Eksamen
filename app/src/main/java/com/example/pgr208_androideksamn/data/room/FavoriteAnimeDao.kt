package com.example.pgr208_androideksamn.data.room

import androidx.room.*

@Dao
interface FavoriteAnimeDao {

    @Query("SELECT * FROM favorite_anime")
    suspend fun getAllFavorites(): List<FavoriteAnimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(anime: FavoriteAnimeEntity)

    @Delete
    suspend fun removeFavorite(anime: FavoriteAnimeEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_anime WHERE malId = :id)")
    suspend fun isFavorite(id: Int): Boolean
}
