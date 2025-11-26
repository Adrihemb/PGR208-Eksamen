package com.example.pgr208_androideksamn


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pgr208_androideksamn.data.anime.AnimeRepository
import com.example.pgr208_androideksamn.navigation.AnimeIdeas
import com.example.pgr208_androideksamn.navigation.AnimeList
import com.example.pgr208_androideksamn.navigation.AnimeSearch
import com.example.pgr208_androideksamn.navigation.BottomNavigationBar
import com.example.pgr208_androideksamn.screens.anime_list.AnimeListScreen
import com.example.pgr208_androideksamn.screens.anime_search.AnimeSearchScreen
import com.example.pgr208_androideksamn.ui.theme.PGR208AndroidEksamnTheme
import com.example.pgr208_androideksamn.data.anime.AnimeIdeaRepository
import com.example.pgr208_androideksamn.screens.anime_ideas.AnimeIdeasScreen



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        AnimeRepository.initialize(this)
        AnimeIdeaRepository.initialize(this)

        setContent {
            PGR208AndroidEksamnTheme {

                //Lager en navController som holder styr på hvilken skjerm som vises,
                // og lar oss navigere mellom de ulike skjermene
                val navController = rememberNavController()

                Box(modifier = Modifier.fillMaxSize()) {

                    Column(modifier = Modifier.fillMaxSize()) {

                        //Navhost er konteineren som viser den aktive skjermen,
                        // og bytter ut innholdet basert på hva som er angitt i navController
                        NavHost(
                            //Kobler navController til NavHost
                            navController = navController,
                            //Angir hvilken skjerm som skal vises først
                            startDestination = AnimeList,
                            //Gjør at NavHost fyller hele skjermen,
                            // og palsserer BottomNavigationBar i bunnen av skjermen
                            modifier = Modifier.weight(1f)
                        ) {
                            //Når NavController navigerer til AnimeList, vises AnimeListScreen
                            composable<AnimeList> {
                                AnimeListScreen(
                                    onAnimeClick = { }
                                )
                            }

                            //Når NavController navigerer til AnimeSearch, vises AnimeSearchScreen
                            composable<AnimeSearch> {
                                AnimeSearchScreen()
                            }

                            //Når NavController navigerer til AnimeIdeas, vises AnimeIdeasScreen
                            composable<AnimeIdeas> {
                                AnimeIdeasScreen()
                            }
                        }
                        //Kaller vår egen Composable-funksjon BottomNavigationBar
                        // som er ansvarlig for å vise navigasjonsbaren nederst.
                        //Sender inn navController som parameter, slik at den kan
                        // håndtere navigasjon når brukeren trykker på et ikon i navigasjonsbaren.
                        BottomNavigationBar(navController = navController)
                    }
                }
            }
        }
    }
}





