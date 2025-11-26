package com.example.pgr208_androideksamn.screens.anime_ideas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pgr208_androideksamn.data.anime.AnimeIdea

//Skjerm #3: Mine anime-ideer
@Composable
fun AnimeIdeasScreen(
    viewModel: AnimeIdeasViewModel = viewModel()
) {
    val loading = viewModel.loading.collectAsState()
    val ideas = viewModel.ideas.collectAsState()
    val error = viewModel.error.collectAsState()

    // Hent ideer når skjermen vises
    LaunchedEffect(Unit) {
        viewModel.loadIdeas()
    }

    // Ekstra funkson, mulighet til å kunne søke på ideene
    var searchText by remember { mutableStateOf("") }

    // Mulighet til å redigere eller lage nye idéer
    var editingIdea by remember { mutableStateOf<AnimeIdea?>(null) }
    var title by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    fun startNewIdea() {
        editingIdea = null
        title = ""
        genre = ""
        description = ""
    }

    fun startEditIdea(idea: AnimeIdea) {
        editingIdea = idea
        title = idea.title
        genre = idea.genre
        description = idea.description
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Overskrift
        Text(text = "Mine anime-ideer")

        Spacer(modifier = Modifier.padding(4.dp))

        // Søkefelt (tilleggskrav)
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Søk i tittel / sjanger") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Feilmeldinger
        error.value?.let { errorMsg ->
            Text(
                text = errorMsg,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Skjema for å legge til eller redigere ide
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Tittel") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = genre,
            onValueChange = { genre = it },
            label = { Text("Sjanger") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Beskrivelse") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    if (title.isBlank()) return@Button

                    if (editingIdea == null) {
                        // Ny idé
                        viewModel.addIdea(title, description, genre)
                    } else {
                        // Oppdater eksisterende ideer
                        viewModel.updateIdea(
                            editingIdea!!.copy(
                                title = title,
                                genre = genre,
                                description = description
                            )
                        )
                    }
                    startNewIdea()
                }
            ) {
                Text(if (editingIdea == null) "Lagre idé" else "Oppdater idé")
            }

            if (editingIdea != null) {
                TextButton(onClick = { startNewIdea() }) {
                    Text("Avbryt redigering")
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        if (loading.value) {                        //Laster data
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val filteredIdeas = ideas.value.filter { idea ->
                idea.title.contains(searchText, ignoreCase = true) ||
                        idea.genre.contains(searchText, ignoreCase = true)
            }

            if (filteredIdeas.isEmpty()) {      //Hvis det ikke finnes noen anime-ideer

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Ingen ideer enda")
                }
            } else {                    //Hvis det finnes anime-ideer
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredIdeas) { idea ->
                        AnimeIdeaItem(
                            idea = idea,
                            onEditClick = { startEditIdea(idea) },
                            onDeleteClick = { viewModel.deleteIdea(idea) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimeIdeaItem(          //Viser en enkelt idé i listen
    idea: AnimeIdea,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = idea.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = idea.genre,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = idea.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.padding(4.dp))

            TextButton(onClick = onEditClick) {
                Text("Rediger")
            }

            TextButton(onClick = onDeleteClick) {
                Text("Slett")
            }
        }
    }
}