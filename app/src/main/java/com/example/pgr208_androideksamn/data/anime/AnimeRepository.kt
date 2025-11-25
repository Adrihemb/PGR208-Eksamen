package com.example.pgr208_androideksamn.data.anime

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.pgr208_androideksamn.data.room.AppDatabase
import com.example.pgr208_androideksamn.data.room.FavoriteAnimeEntity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AnimeRepository {

    private val httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    private val retrofit =
        Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.jikan.moe/v4/")
            .build()

    private val _animeService = retrofit.create(AnimeService::class.java)

    private lateinit var _appDatabase: AppDatabase

    private val _animeDao by lazy {
        _appDatabase.animeDao()
    }

    // DAO for favoritter (ny)
    private val _favoriteAnimeDao by lazy {
        _appDatabase.favoriteAnimeDao()
    }

    fun initialize(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "anime_database"
        )

            .fallbackToDestructiveMigration()
            .build()
    }


    // -------- API + DATABASE (finnes fra før) ------------------

    suspend fun getAllAnimes(): List<Anime> {
        try {
            val response = _animeService.getAllAnimes()
            if (response.isSuccessful) {
                val animesFromApi = response.body()?.data ?: emptyList()
                _animeDao.insertAnime(animesFromApi)
                return _animeDao.getAllAnimes()
            } else {
                throw Exception("Unsuccessful response from API. Code: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("AnimeRepository", "Failed to refresh animes: ${e.message}", e)
            return _animeDao.getAllAnimes()
        }
    }

    suspend fun getAnimeByIdFromDb(id: Int): Anime? {
        return _animeDao.getAnimeById(id)
    }

    // -------- FAVORITT-FUNKSJONALITET (ny) ------------------

    suspend fun addFavorite(entity: FavoriteAnimeEntity) {
        _favoriteAnimeDao.addFavorite(entity)
    }

    suspend fun removeFavorite(entity: FavoriteAnimeEntity) {
        _favoriteAnimeDao.removeFavorite(entity)
    }

    suspend fun getAllFavorites(): List<FavoriteAnimeEntity> {
        return _favoriteAnimeDao.getAllFavorites()
    }

    suspend fun isFavorite(id: Int): Boolean {
        return _favoriteAnimeDao.isFavorite(id)
    }
}
