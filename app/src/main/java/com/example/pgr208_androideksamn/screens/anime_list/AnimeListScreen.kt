package com.example.pgr208_androideksamn.screens.anime_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AnimeListScreen(
    viewModel: AnimeListViewModel = viewModel(),
    onAnimeClick: (Int) -> Unit
) {
    val loading = viewModel.loading.collectAsState()
    val animeList = viewModel.animeList.collectAsState()
    val error = viewModel.error.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.fetchAnimes()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            loading.value -> {
                CircularProgressIndicator()
            }

            error.value != null -> {
                Text(text = "Feil: ${error.value}")
            }

            animeList.value.isNotEmpty() -> {

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(animeList.value) { anime ->
                        AnimeItem(
                            anime = anime,
                            onAnimeClick = { onAnimeClick(anime.id) }
                        )
                    }
                }
            }
        }
    }
}
