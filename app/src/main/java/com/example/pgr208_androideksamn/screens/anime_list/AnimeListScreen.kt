package com.example.pgr208_androideksamn.ui.screens.anime_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pgr208_androideksamn.screens.anime_list.AnimeItem

@Composable
fun AnimeListScreen(
    viewModel: AnimeListViewModel = viewModel(),
    onAnimeClick: (Int) -> Unit
) {
    val loading = viewModel.loading.collectAsState()
    val animeList = viewModel.animeList.collectAsState()

    if (loading.value) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(animeList.value) { anime ->
            AnimeItem(
                anime = anime,
                onAnimeClick = { onAnimeClick(anime.id) }
            )
        }
    }
}
