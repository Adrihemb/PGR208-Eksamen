package com.example.pgr208_androideksamn.screens.anime_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_androideksamn.data.anime.Anime
import com.example.pgr208_androideksamn.data.anime.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//Viewmodelen for AnimeListScreen. Har ansvar for å hente og håndtere data for anime-listet
class AnimeListViewModel : ViewModel() {

    //Holder styr på om en søk på nettet er igang eller ikke
    private val _loading = MutableStateFlow(false)

    val loading = _loading.asStateFlow()

    //Holder styr på listen med anime-objekter som skal vises på skjermen, starter som en tom liste
    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())

    val animeList: StateFlow<List<Anime>> = _animeList.asStateFlow()

    //Holder styr på feilmeldinger som skal vises til brukeren, kan være null om det ikke er noen feil
    private val _error = MutableStateFlow<String?>(null)

    val error: StateFlow<String?> = _error.asStateFlow()



    //Funksjon som henter listen med anime-objekter fra AnimeRepository
    fun fetchAnimes() {
        //Starter coroutine som kjører i viewModelScope
        viewModelScope.launch {

            //Sjekker om vi allerede er i gang med å laste, for å unngå å starte flere operasjoner samtidig
            if (!_loading.value)

            //Lager en try-catch-finally blokk for å håndtere feil på en robust måte
            try {
                //Sett loading til true og nullstill tidligere feilmeldinger
                _loading.value = true
                _error.value = null

                //Hent data fra AnimeRepository og oppdaterer animeList-listen hvis hentinger var velykket
                val animes = AnimeRepository.getAllAnimes()
                _animeList.value = animes
            }
            //Håndterer feil ved henting av data og oppdaterer feilmeldingen
            catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            }
            //Denne blokken kjører uansett om det oppstår feil eller ikke
            //Setter loading til false for å fjerne loading-indikatoren
            finally {
                _loading.value = false
            }
        }
    }
}
