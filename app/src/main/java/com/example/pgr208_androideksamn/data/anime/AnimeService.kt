package com.example.pgr208_androideksamn.data.anime

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeService {

    //Henter alle anime fra API
    @GET("anime")
    suspend fun getAllAnimes(): Response<AnimeApiResponse>

    //Henter detaljert innformasjon om et spesifikt anime basert på ID
    @GET("anime/{id}")
    suspend fun getAnimeById(
        @Path("id") id: Int
    ): Response<AnimeDetailResponse>
}


