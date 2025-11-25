package com.example.pgr208_androideksamn.screens.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FavoritesFilterSection(
    minScore: Float,
    onMinScoreChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(text = "Min. score: ${minScore}")

        Slider(
            value = minScore,
            onValueChange = { newValue ->
                // Rund av til nærmeste halvdel
                val rounded = (newValue * 2).toInt() / 2f
                onMinScoreChange(rounded)
            },
            valueRange = 0f..10f,
            steps = 19    // hele og halve tall
        )
    }
}
