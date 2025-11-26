package com.example.pgr208_androideksamn.screens.anime_ideas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_androideksamn.data.anime.AnimeIdea
import com.example.pgr208_androideksamn.data.anime.AnimeIdeaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//ViewModel for skjermen "Mine ideer"
class AnimeIdeasViewModel : ViewModel() {

    //Laster/ikke-laster
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    //Liste over alle ideer
    private val _ideas = MutableStateFlow<List<AnimeIdea>>(emptyList())
    val ideas = _ideas.asStateFlow()

    //Enkel feilmeldingstekst
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun loadIdeas() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _ideas.value = AnimeIdeaRepository.getAllIdeas()
            } catch (e: Exception) {
                _error.value = e.message ?: "Ukjent feil ved henting av ideer"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addIdea(title: String, description: String, genre: String) {
        viewModelScope.launch {
            try {
                AnimeIdeaRepository.addIdea(title, description, genre)
                _ideas.value = AnimeIdeaRepository.getAllIdeas()
            } catch (e: Exception) {
                _error.value = e.message ?: "Feil ved lagring av idé"
            }
        }
    }

    fun updateIdea(idea: AnimeIdea) {
        viewModelScope.launch {
            try {
                AnimeIdeaRepository.updateIdea(idea)
                _ideas.value = AnimeIdeaRepository.getAllIdeas()
            } catch (e: Exception) {
                _error.value = e.message ?: "Feil ved oppdatering av idé"
            }
        }
    }

    fun deleteIdea(idea: AnimeIdea) {
        viewModelScope.launch {
            try {
                AnimeIdeaRepository.deleteIdea(idea)
                _ideas.value = AnimeIdeaRepository.getAllIdeas()
            } catch (e: Exception) {
                _error.value = e.message ?: "Feil ved sletting av idé"
            }
        }
    }
}