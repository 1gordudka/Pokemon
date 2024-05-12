package com.igordudka.pokemon.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igordudka.pokemon.model.Result
import com.igordudka.pokemon.repository.PokemonRepository
import com.igordudka.pokemon.ui.state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject
constructor(private val pokemonRepository: PokemonRepository) : ViewModel(){

    private val _listState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val listState = _listState.asStateFlow()

    fun getList(){
        viewModelScope.launch(Dispatchers.IO) {
            _listState.value = ScreenState.Loading
            try {
                val body = pokemonRepository.getPokemons().execute().body()
                _listState.value = ScreenState.Success(data = body as Any)
            }catch (e: Exception){
                _listState.value = ScreenState.Failed
                Log.e("ERROR", e.toString())
            }
        }
    }
}