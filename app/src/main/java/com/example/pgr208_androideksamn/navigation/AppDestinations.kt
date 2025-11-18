package com.example.pgr208_androideksamn.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
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

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val screenRoute: Any
)

val items = listOf(
    BottomNavItem(
        title = "Anime liste",
        icon = Icons.Default.List,
        screenRoute = AnimeList
    ),
    BottomNavItem(
        title = "Anime søk",
        icon = Icons.Default.Search,
        screenRoute = AnimeSearch
    ),
    BottomNavItem(
        title = "Mine ideer",
        icon = Icons.Default.Create,
        screenRoute = AnimeIdeas
    )
)

