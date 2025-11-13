package com.example.pgr208_androideksamn.ui.screens.anime_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_androideksamn.data.anime.Anime
import com.example.pgr208_androideksamn.data.anime.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeListViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)

    val loading = _loading.asStateFlow()


    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())

    val animeList: StateFlow<List<Anime>> = _animeList.asStateFlow()

    init {
        fetchAnimes()
    }

    private fun fetchAnimes() {
        viewModelScope.launch {

            _loading.value = true

            val animes = AnimeRepository.getAllAnimes()
            _animeList.value = animes

            _loading.value = false
        }
    }
}
