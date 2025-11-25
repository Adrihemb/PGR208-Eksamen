package com.example.pgr208_androideksamn.screens.anime_search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pgr208_androideksamn.screens.anime_list.AnimeItem

//Skjerm for søk etter anime basert på animeId
//Bruker AnimeSearchViewModel for å håndtere logikk for søkets logikk
@Composable
fun AnimeSearchScreen(
    //Henter en instans av AnimeSearchViewModel
    viewModel: AnimeSearchViewModel = viewModel()
) {
    val searchResults = viewModel.searchResults.collectAsState()
    val loading = viewModel.loading.collectAsState()
    val error = viewModel.error.collectAsState()

    //"remember" for å lagre input fra brukeren og feilmeldinger
    //"searchQuery" for å holde på teksten som brukeren skriver inn i søkefeltet
    var searchQuery = remember { mutableStateOf("") }

    //Lagrer en eventuel feilmelding for input, og gjør at den blir værende på skjermen
    var inputError = remember { mutableStateOf<String?>(null) }


    //Hovedlayout for skjermen
    Column(
        modifier = Modifier
            //Fyller hele skjermen
            .fillMaxSize()
            //Legger til padding rundt innholdet
            .padding(12.dp),
        //Sentrerer innholdet horisontalt
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Rad som inneholder søkefelt og søk-knappen
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            //Tekstfelt for å skrive inn animeId
            OutlinedTextField(
                value = searchQuery.value,
                //Kjøres hver gang brukeren endrer teksten i feltet
                onValueChange = {
                    //Oppdaterer teksten når brukeren endrer den
                    searchQuery.value = it
                    //Fjerner feilmeldingen hvis den finnes
                    inputError.value = null
                },
                //Tekst som vises i søkefeltet
                label = { Text("Søk etter animeId") },
                //Gjør at brukeren kunn kan skrive inn tall
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                //Gjør at feltet kan bruke all tilgjengelige plass
                modifier = Modifier.weight(1f),
                //Gjør at teksten ikke kan overstige en linje
                singleLine = true,
                //Viser en rød ramme hvis input er feil
                isError = inputError.value != null
            )
            Spacer(
                //Gjør at det blirr litt luft mellom tekstfeltet og søk-knappen
                modifier = Modifier.width(10.dp)
            )
            //Søkeknappen
            Button(
                onClick = {
                    //Når knappen trykkes sjekker vi først om feltet er tomt
                    if (searchQuery.value.isBlank()) {
                        //Hvis feltet er tomt viser vi en feilmelding
                        inputError.value = "Vennligst skriv inn en Id"
                    } else {
                        //Hvis feltet ikke er tomt prøver vi å konventere teksten til et tall,
                        // selv om vi har implementert "KeyboardOptions(keyboardType = KeyboardType.Number)"
                        // tidligere i koden for ekstra sikkerhet
                        val animeId = searchQuery.value.toIntOrNull()

                        //Sjekker om konverteringen var vellykket
                        if (animeId != null) {
                            inputError.value = null
                            //Kaller funksjonen i viewModel for å gjøre søket
                            viewModel.animeSearchById(animeId)
                            //Hvis ikke viser vi en feilmelding
                        } else {
                            inputError.value = "Ugyldig animeId"
                        }
                    }
                }
            ) {
                //Tekst som vises på knappen
                Text("Søk")
            }
        }

        //Visning av feilmelding hvis input er feil
        //Hvis errorMessage ikke er null viser vi den til brukeren
        if (inputError.value != null) {
            Text(
                text = inputError.value!!,
                color = Color.Red,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        //Visning av resultat dersom søket er ferdig
        //if/else if gjør at vi kunn viser en av de mulige tilstandene om gangen
        //Hvis loading er true viser vi "Laster..."
        if (loading.value) {
            Text("Laster...")
            //Hvis searchResults ikke er null viser vi anime-objektet som ble funnet
        } else if (searchResults.value != null) {
            AnimeItem(anime = searchResults.value!!)
            //Hvis searchResults er null og loading er false viser vi en feilmelding
        } else if (error.value != null) {
            Text(error.value!!)
        }
    }
}