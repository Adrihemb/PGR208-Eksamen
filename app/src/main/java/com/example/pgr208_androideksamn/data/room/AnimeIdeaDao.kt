package com.example.pgr208_androideksamn.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pgr208_androideksamn.data.anime.AnimeIdea

@Dao
interface AnimeIdeaDao {
    @Query("SELECT * FROM anime_ideas ORDER BY id DESC")    //Sorterer etter id i synkende rekkefølge
    suspend fun getAllIdeas(): List<AnimeIdea>
    @Query("SELECT * FROM anime_ideas WHERE id = :id")      //Henter en spesifikk idé basert på id
    suspend fun getIdeaById(id: Int): AnimeIdea?
    @Insert(onConflict = OnConflictStrategy.REPLACE)                //Lagrer en ny idé
    suspend fun insertIdea(idea: AnimeIdea): Long
    @Update                                                     //Oppdaterer en eksisterende idé
    suspend fun updateIdea(idea: AnimeIdea)
    @Delete                                                     //Sletter en eksisterende idé
    suspend fun deleteIdea(idea: AnimeIdea)
}