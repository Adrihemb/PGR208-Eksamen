package com.example.pgr208_androideksamn.data.anime

import android.util.Log
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
    private val animeService = retrofit.create(AnimeService::class.java)


    //Funksjon som henter listen med alle animer fra API-et
    suspend fun getAllAnimes(): List<Anime> {
        //try-catch-blokken håndterer feil ved kall til API
        try {
            //Kaller funksjonen fra AnimeService som henter listen med anime-objekter
            val response = animeService.getAllAnimes()

            //Sjekker om kall var vellykket og returnerer listen med anime-objekter
            if (response.isSuccessful) {
                //Hvis kall var vellykket, returnerer vi listen med anime-objekter fra responsen
                return response.body()?.data ?: emptyList()
            } else {
                //Hvis kall var ikke vellykket, loggres feilmeldingen og en tom liste returneres
                Log.e("AnimeRepository", "Unsuccessful response. Code: ${response.code()}")
                return emptyList()
            }
        } catch (e: Exception) {
            Log.e("AnimeRepository", "Exception (e.g., no network): ${e.message}")
            return emptyList()
        }
    }


    suspend fun getAnimeById(id: Int): Anime? {
        try {
            val response = animeService.getAnimeById(id)

            if (response.isSuccessful) {
                return response.body()?.data
            } else {
                Log.e(
                    "AnimeRepository",
                    "Unsuccessful response for ID $id. Code: ${response.code()}"
                )
                return null
            }
        } catch (e: Exception) {
            Log.e("AnimeRepository", "Exception when getting anime by ID $id: ${e.message}")
            return null
        }
    }
}




