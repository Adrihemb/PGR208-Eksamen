package com.example.pgr208_androideksamn.screens.anime_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.pgr208_androideksamn.data.anime.Anime
import com.example.pgr208_androideksamn.data.anime.Images
import com.example.pgr208_androideksamn.data.anime.Jpg

@Composable
fun AnimeItem(
    anime: Anime,
    modifier: Modifier = Modifier,
    onAnimeClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onAnimeClick() }
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Bilde
        AsyncImage(
            model = anime.images.jpg.imageUrl,
            contentDescription = anime.title,
            modifier = Modifier
                .size(80.dp)
        )

        Spacer(modifier = Modifier.size(8.dp))

        // Tekstinnhold
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = anime.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Score: ${anime.score ?: "-"}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // 🔹 Favoritt-knapp
        IconButton(
            onClick = onFavoriteClick
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Legg til som favoritt"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeItemPreview() {
    AnimeItem(
        anime = Anime(
            id = 1,
            title = "Fullmetal Alchemist: Brotherhood",
            score = 9.09,
            synopsis = "...",
            images = Images(
                jpg = Jpg(
                    imageUrl = "https://cdn.myanimelist.net/images/anime/1223/96541.jpg"
                )
            )
        )
    )
}
