package com.example.pgr208_androideksamn.screens.anime_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_androideksamn.data.anime.Anime
import com.example.pgr208_androideksamn.data.anime.AnimeRepository
import com.example.pgr208_androideksamn.data.room.FavoriteAnimeEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeListViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList: StateFlow<List<Anime>> = _animeList.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchAnimes() {
        viewModelScope.launch {
            _loading.value = true

            try {
                _error.value = null
                val animes = AnimeRepository.getAllAnimes()
                _animeList.value = animes
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            } finally {
                _loading.value = false
            }
        }
    }

    // 🔹 NY: legg til anime i favoritter
    fun addFavorite(anime: Anime) {
        viewModelScope.launch {
            val entity = FavoriteAnimeEntity(
                malId = anime.id,
                title = anime.title,
                imageUrl = anime.images.jpg.imageUrl,
                type = null,          // du kan fylle inn hvis dere bruker type
                score = anime.score
            )

            AnimeRepository.addFavorite(entity)
        }
    }
}
