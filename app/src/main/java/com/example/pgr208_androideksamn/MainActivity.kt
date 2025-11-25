package com.example.pgr208_androideksamn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pgr208_androideksamn.data.anime.AnimeRepository
import com.example.pgr208_androideksamn.navigation.AnimeIdeas
import com.example.pgr208_androideksamn.navigation.AnimeList
import com.example.pgr208_androideksamn.navigation.AnimeSearch
import com.example.pgr208_androideksamn.navigation.BottomNavigationBar
import com.example.pgr208_androideksamn.navigation.Favorites
import com.example.pgr208_androideksamn.screens.anime_list.AnimeListScreen
import com.example.pgr208_androideksamn.screens.anime_list.AnimeListViewModel
import com.example.pgr208_androideksamn.screens.anime_search.AnimeSearchScreen
import com.example.pgr208_androideksamn.screens.favorite.FavoritesScreen
import com.example.pgr208_androideksamn.screens.favorite.FavoritesViewModel
import com.example.pgr208_androideksamn.ui.theme.PGR208AndroidEksamnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialiser Room-databasen
        AnimeRepository.initialize(this)

        setContent {
            PGR208AndroidEksamnTheme {

                val navController = rememberNavController()

                Box(modifier = Modifier.fillMaxSize()) {

                    Column(modifier = Modifier.fillMaxSize()) {

                        NavHost(
                            navController = navController,
                            startDestination = AnimeList,
                            modifier = Modifier.weight(1f)
                        ) {

                            // Skjerm 1 – Anime liste
                            composable<AnimeList> {
                                val listViewModel: AnimeListViewModel = viewModel()

                                AnimeListScreen(
                                    viewModel = listViewModel,
                                    onAnimeClick = { /* detalj senere */ }
                                )
                            }

                            // Skjerm 2 – Søk
                            composable<AnimeSearch> {
                                AnimeSearchScreen()
                            }

                            // Skjerm 3 – Egne ideer
                            composable<AnimeIdeas> {
                                // Todo: AnimeIdeasScreen()
                            }

                            // Skjerm 4 – Favoritter
                            composable<Favorites> {
                                val favoritesViewModel: FavoritesViewModel = viewModel()

                                FavoritesScreen(
                                    viewModel = favoritesViewModel,
                                    onAnimeClick = { /* evt. naviger til info */ }
                                )
                            }
                        }

                        // Bunnmenyen
                        BottomNavigationBar(navController = navController)
                    }
                }
            }
        }
    }
}
