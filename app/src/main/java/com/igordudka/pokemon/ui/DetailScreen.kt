package com.igordudka.pokemon.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.igordudka.pokemon.model.PokemonDetail
import com.igordudka.pokemon.ui.state.ScreenState
import com.igordudka.pokemon.viewmodel.PokemonDetailViewModel

@Composable
fun DetailScreen(
    id: String,
    pokemonDetailViewModel: PokemonDetailViewModel = hiltViewModel()
) {

    val pokemonState by pokemonDetailViewModel.pokemonState.collectAsState()

    LaunchedEffect(true) {
        pokemonDetailViewModel.getPokemon(id)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        when(pokemonState){
            ScreenState.Failed -> {
                Column(Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(text = "Error", style = MaterialTheme.typography.displayMedium)
                }
            }
            ScreenState.Loading -> {
                Column(Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    CircularProgressIndicator()
                }
            }
            is ScreenState.Success -> {
                ((pokemonState as ScreenState.Success).data as PokemonDetail).let { pokemon->
                    pokemon.name?.let { Text(text = it, style = MaterialTheme.typography.displayLarge) }
                    pokemon.id?.let { Text(text = "Id: $it", style = MaterialTheme.typography.displaySmall) }
                    pokemon.height?.let { Text(text = "Height: $it", style = MaterialTheme.typography.bodyMedium) }
                    pokemon.weight?.let { Text(text = "Weight: $it", style = MaterialTheme.typography.bodyMedium) }
                }
            }
        }
    }
}