package com.example.pgr208_androideksamn.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_androideksamn.data.anime.Anime
import com.example.pgr208_androideksamn.data.anime.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeDetailsViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)

    val loading = _loading.asStateFlow()

    private val _selectedAnime = MutableStateFlow<Anime?>(null)

    val selectedAnime = _selectedAnime.asStateFlow()


    fun getAnimeDetails (id: Int) {

        viewModelScope.launch {
            _loading.value = true
            _selectedAnime.value = AnimeRepository.getAnimeByIdFromDb(id)
            _loading.value = false
        }
    }
}