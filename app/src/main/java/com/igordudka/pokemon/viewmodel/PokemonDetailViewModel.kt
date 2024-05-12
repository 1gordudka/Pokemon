package com.igordudka.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igordudka.pokemon.model.PokemonDetail
import com.igordudka.pokemon.repository.PokemonRepository
import com.igordudka.pokemon.ui.state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject
constructor(private val pokemonRepository: PokemonRepository) : ViewModel(){

    private val _pokemonState = MutableStateFlow<ScreenState>(
        ScreenState.Loading
    )
    val pokemonState = _pokemonState.asStateFlow()

    fun getPokemon(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            _pokemonState.value = ScreenState.Loading
            try {
                val body = pokemonRepository.getDetailPokemon(id).execute().body()
                if (body != null) {
                    _pokemonState.value = ScreenState.Success(data = body)
                }
            }catch (e: Exception){
                _pokemonState.value = ScreenState.Failed
            }
        }
    }
}