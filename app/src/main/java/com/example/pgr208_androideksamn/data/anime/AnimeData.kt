package com.example.pgr208_androideksamn.data.anime

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "anime")
data class Anime(
    //@SerializedName("mal_id") forteller Gson hvordan den skal oversette fra JSON
    //JSON-feltet med verdien "mal_id" gjør vi til variabelen "id" i koden vår
    @PrimaryKey
    @SerializedName("mal_id")
    val id: Int,

    //Vanlige felt som hentes direkte fra JSON basert på navnet
    val title: String,
    val synopsis: String?,
    val score: Double?,

    //images er et objekt som inneholder flere underobjekter
    val images: Images
)

data class Images(
    //Inne i images er et nytt objekt som heter jpg
    @SerializedName("jpg")
    val jpg: Jpg
)

data class Jpg(
    //Inne i jpg er det endelige URL-en til bildet
    @SerializedName("image_url")
    val imageUrl: String
)

data class AnimeApiResponse(
    //"data" er navnet på listen med anime-objekter i JSON-objektet
    val data: List<Anime>
)

data class AnimeDetailResponse(
    val data: Anime
)

