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
    onAnimeClick: (Int) -> Unit,
    viewModel: AnimeListViewModel
) {
    val viewModel: AnimeListViewModel = viewModel()

    val loading = viewModel.loading.collectAsState()
    val animeList = viewModel.animeList.collectAsState()
    val error = viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAnimes()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                loading.value -> {
                    CircularProgressIndicator()
                }

                error.value != null -> {
                    Text(text = error.value ?: "En feil oppstod")
                }

                animeList.value.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        items(animeList.value) { anime ->
                            AnimeItem(
                                anime = anime,
                                onAnimeClick = { onAnimeClick(anime.id) },
                                onFavoriteClick = {
                                    viewModel.addFavorite(anime)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
