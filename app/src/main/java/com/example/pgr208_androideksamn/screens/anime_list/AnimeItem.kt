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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.pgr208_androideksamn.data.anime.Anime
import com.example.pgr208_androideksamn.data.anime.Images
import com.example.pgr208_androideksamn.data.anime.Jpg

@Composable
fun AnimeItem(
    anime: Anime,
    modifier: Modifier = Modifier,
    onAnimeClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable {onAnimeClick()}
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .background(color = Color.White),

        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(108.dp, 108.dp)
                .background(color = Color.Gray),
            model = anime.images.jpg.imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = "Bilde for ${anime.title}"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding( 16.dp),
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = anime.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Rating: ${anime.score}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
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