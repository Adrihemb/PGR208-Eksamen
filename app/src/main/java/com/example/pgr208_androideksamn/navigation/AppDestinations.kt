package com.example.pgr208_androideksamn.navigation

import kotlinx.serialization.Serializable

@Serializable
object AnimeList

@Serializable
object AnimeSearch

@Serializable
object AnimeIdeas

@Serializable
object Ubestemt

@Serializable
data class AnimeDetail(
    val animeId: Int
)