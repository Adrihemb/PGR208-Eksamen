package com.example.pgr208_androideksamn.screens.anime_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_androideksamn.data.anime.Anime
import com.example.pgr208_androideksamn.data.anime.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeSearchViewModel : ViewModel() {

    private val _searchResults = MutableStateFlow<Anime?>(null)
    val searchResults = _searchResults.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()



    fun animeSearchById(id: Int) {

        viewModelScope.launch {

            _loading.value = true
            _searchResults.value = null
            _error.value = null

            val result = AnimeRepository.getAnimeById(id)

            if (result != null) {
                _searchResults.value = result
            } else {
                _error.value = "Kunne ikke finne anime med ID $id"
            }
                _loading.value = false
        }
    }
}
