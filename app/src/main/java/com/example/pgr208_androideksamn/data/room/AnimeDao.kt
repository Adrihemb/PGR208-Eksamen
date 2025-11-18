package com.example.pgr208_androideksamn.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pgr208_androideksamn.data.anime.Anime

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime")
    suspend fun getAllAnimes(): List<Anime>

    @Query("SELECT * FROM anime WHERE id = :id")
    suspend fun getAnimeById(id: Int): Anime?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: List<Anime>)
}