package com.example.pgr208_androideksamn.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage

@Composable
fun AnimeDetailsScreen(

    //Vi henter viewmodel-en til skjermen her fra AnimeDetailsViewModel
    viewModel: AnimeDetailsViewModel = viewModel()
) {
    val loading = viewModel.loading.collectAsState()
    val selectedAnime = viewModel.selectedAnime.collectAsState()

    if (loading.value) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val anime = selectedAnime.value
    if (anime == null) {
        Text(text = "Kunne ikke laste detaljer for animen")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp),
    ) {
        AsyncImage(
            model = anime.images.jpg.imageUrl,
            contentDescription = "Bilde for ${anime.title}",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = anime.title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (anime.synopsis != null) {
            Text(
                text = "Rating: ${anime.score}",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (anime.synopsis != null) {
            Text(
                text = anime.synopsis,
                color = Color.Black,
            )
        }
    }
}

