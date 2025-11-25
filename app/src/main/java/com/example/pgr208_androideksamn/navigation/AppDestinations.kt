package com.example.pgr208_androideksamn.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

// Hver skjerm i appen representeres som et serialiserbart objekt
@Serializable
object AnimeList

@Serializable
object AnimeSearch

@Serializable
object AnimeIdeas

@Serializable
object Favorites   // 🔹 Skjerm 4: favoritt-skjerm

// Modell for et bottom-nav element
data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val screenRoute: Any
)

// Listen som BottomNavigationBar bruker
val bottomNavItems = listOf(
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
    ),
    BottomNavItem(
        title = "Favoritter",
        icon = Icons.Default.Favorite,
        screenRoute = Favorites   // 🔹 vår nye skjerm
    )
)
