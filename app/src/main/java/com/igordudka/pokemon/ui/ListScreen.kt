package com.igordudka.pokemon.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.igordudka.pokemon.model.PokemonResponse
import com.igordudka.pokemon.ui.state.ScreenState
import com.igordudka.pokemon.viewmodel.PokemonListViewModel

@Composable
fun ListScreen(
    goToDetail: (String) -> Unit,
    pokemonListViewModel: PokemonListViewModel = hiltViewModel()
) {

    val listState by pokemonListViewModel.listState.collectAsState()

    LaunchedEffect(true) {
        pokemonListViewModel.getList()
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        Text(text = "Pokemons", style = MaterialTheme.typography.displayLarge)
        when(listState){
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
                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    items(((listState as ScreenState.Success).data as PokemonResponse).results!!){
                        if (it != null) {
                            it.name?.let { it1 -> PokemonItem(onClick = { it.url?.let { it1 -> goToDetail(it1) } }, name = it1) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonItem(
    onClick: () -> Unit,
    name: String
) {
    Row (
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable { onClick() }
            .padding(12.dp)){
        Text(text = name, style = MaterialTheme.typography.bodyMedium)
    }
}