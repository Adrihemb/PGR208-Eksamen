package com.example.pgr208_androideksamn.data.anime

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.pgr208_androideksamn.data.room.AppDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AnimeRepository {

    //Setter opp en HTTP-klient
    //Denne håndterer sendinene og mottakene av data fra API
    //Interceptor legger til logging for å kunne se hva som skjer i logcat
    private val httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    //Setter opp en Retrofit-instans for å kommunisere med API'et
    //Legger til en GsonConverterFactory som konverterer JSON-data til Kotlin-objekter
    //Setter en base-URL for API-kall, alle kall i AnimeService vil starte med denne adressen
    private val retrofit =
        Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.jikan.moe/v4/")
            .build()

    //Lager en instans av AnimeService som bruker retrofit for å lage API-kall
    private val _animeService = retrofit.create(AnimeService::class.java)

    private lateinit var _appDatabase: AppDatabase

    private val _animeDao by lazy {
        _appDatabase.animeDao()
    }

    fun initialize(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "anime_database"
        ).build()
    }


    //Funksjon som henter listen med alle animer fra API-et
    suspend fun getAllAnimes(): List<Anime> {
        //try-catch-blokken håndterer feil ved kall til API
        try {
            //Kaller funksjonen fra AnimeService som henter listen med anime-objekter
            val response = _animeService.getAllAnimes()

            //Sjekker om kall var vellykket og returnerer listen med anime-objekter
            if (response.isSuccessful) {
                //Hvis kall var vellykket, returnerer vi listen med anime-objekter fra responsen
                val animesFromApi = response.body()?.data ?: emptyList()

                _animeDao.insertAnime(animesFromApi)

                return _animeDao.getAllAnimes()
            } else {
                //Hvis kall var ikke vellykket, loggres feilmeldingen og en tom liste returneres
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
}





