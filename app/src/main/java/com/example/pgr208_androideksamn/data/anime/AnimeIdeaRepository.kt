package com.example.pgr208_androideksamn.data.anime

import android.content.Context
import androidx.room.Room
import com.example.pgr208_androideksamn.data.room.AppDatabase

object AnimeIdeaRepository {
    private lateinit var _appDatabase: AppDatabase
    private val _animeIdeaDao by lazy {
        _appDatabase.animeIdeaDao()
    }
    fun initialize(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "anime_database"
        ).build()
    }
    //Henter alle ideer
    suspend fun getAllIdeas(): List<AnimeIdea> {
        return _animeIdeaDao.getAllIdeas()
    }
    //Legger til en ny idé
    suspend fun addIdea(title: String, description: String, genre: String) {
        val idea = AnimeIdea(
            title = title,
            description = description,
            genre = genre
        )
        _animeIdeaDao.insertIdea(idea)
    }
    //Oppdaterer en eksisterende idé
    suspend fun updateIdea(idea: AnimeIdea) {
        _animeIdeaDao.updateIdea(idea)
    }
    //Sletter en idé
    suspend fun deleteIdea(idea: AnimeIdea) {
        _animeIdeaDao.deleteIdea(idea)
    }
}