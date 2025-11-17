package com.example.pgr208_androideksamn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pgr208_androideksamn.navigation.AnimeIdeas
import com.example.pgr208_androideksamn.navigation.AnimeList
import com.example.pgr208_androideksamn.navigation.AnimeSearch
import com.example.pgr208_androideksamn.navigation.BottomNavigationBar
import com.example.pgr208_androideksamn.ui.screens.anime_list.AnimeListScreen
import com.example.pgr208_androideksamn.ui.theme.PGR208AndroidEksamnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PGR208AndroidEksamnTheme {

                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigationBar(navController = navController) }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = AnimeList,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<AnimeList> {
                            AnimeListScreen(
                                onAnimeClick = { }
                            )
                        }

                        composable<AnimeSearch> {

                        }


                        composable<AnimeIdeas> {

                        }
                    }
                }
            }
        }
    }
}



