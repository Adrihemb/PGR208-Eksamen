package com.example.pgr208_androideksamn.screens.anime_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_androideksamn.data.anime.Anime
import com.example.pgr208_androideksamn.data.anime.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//ViewModel for søk etter anime basert på animeId
class AnimeSearchViewModel : ViewModel() {

    //Holder styr på søkeresultater, kan være null om søket feiler
    private val _searchResults = MutableStateFlow<Anime?>(null)
    val searchResults = _searchResults.asStateFlow()

    //Holder styr på om en søk på nettet er igang eller ikke
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    //Holder styr på feilmeldinger som skal vises til brukeren, kan være null om det ikke er noen feil
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()


    //Funksjon som håndterer søk etter anime basert på animeId
    fun animeSearchById(id: Int) {

        //Starter coroutine som kjører i viewModelScope
        viewModelScope.launch {

            //Viser en laseindikator på skjermen mens søket kjører
            _loading.value = true
            //Tømmer forrige søkeresultat
            _searchResults.value = null
            //Tømmer feilmeldingen
            _error.value = null

            //Henter resultat fra den lokale databasen
            val resultFromDb = AnimeRepository.getAnimeByIdFromDb(id)

            //Sjekker om resultatet er null, altså om det ikke finnes i databasen
            if (resultFromDb != null) {
                _searchResults.value = resultFromDb
            } else {
                //Hvis det ikke finnes i databasen, print feilmelding
                _error.value = "Kunne ikke finne anime med ID $id i API-et"
            }
                _loading.value = false
        }
    }
}
