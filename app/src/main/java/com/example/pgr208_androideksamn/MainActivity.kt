package com.example.pgr208_androideksamn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pgr208_androideksamn.ui.screens.anime_list.AnimeListScreen
import com.example.pgr208_androideksamn.ui.theme.PGR208AndroidEksamnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PGR208AndroidEksamnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AnimeApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AnimeApp(modifier: Modifier = Modifier) {
    AnimeListScreen()
}

