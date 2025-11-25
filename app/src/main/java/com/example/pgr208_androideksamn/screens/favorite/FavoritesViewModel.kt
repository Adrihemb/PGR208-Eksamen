package com.example.pgr208_androideksamn.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_androideksamn.data.anime.AnimeRepository
import com.example.pgr208_androideksamn.data.room.FavoriteAnimeEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FavoritesUiState(
    val allFavorites: List<FavoriteAnimeEntity> = emptyList(),
    val filteredFavorites: List<FavoriteAnimeEntity> = emptyList(),
    val minScore: Float = 0f,
    val isLoading: Boolean = false,
    val error: String? = null
)

class FavoritesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadFavorites()
    }

    /**
     * Henter alle favoritter fra databasen og oppdaterer state.
     */
    private fun loadFavorites() {
        viewModelScope.launch {
            // sett loading = true og nullstill error
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val favorites = AnimeRepository.getAllFavorites()

                // lagre alle favoritter og skru av loading
                _uiState.value = _uiState.value.copy(
                    allFavorites = favorites,
                    isLoading = false
                )

                // bruk gjeldende filter (minScore) på lista
                applyFilters()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Kunne ikke hente favoritter"
                )
            }
        }
    }

    /**
     * Kalles når brukeren endrer minimum score i slideren.
     */
    fun onMinScoreChange(newScore: Float) {
        _uiState.value = _uiState.value.copy(minScore = newScore)
        applyFilters()
    }

    /**
     * Fjerner en favoritt og laster listen på nytt.
     */
    fun removeFavorite(anime: FavoriteAnimeEntity) {
        viewModelScope.launch {
            AnimeRepository.removeFavorite(anime)
            loadFavorites()
        }
    }

    /**
     * Filtrerer favorittene basert på minScore.
     */
    private fun applyFilters() {
        val currentState = _uiState.value

        val filtered = currentState.allFavorites.filter { fav ->
            (fav.score ?: 0.0) >= currentState.minScore
        }

        _uiState.value = currentState.copy(filteredFavorites = filtered)
    }
}
