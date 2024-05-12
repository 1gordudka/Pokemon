package com.igordudka.pokemon.ui.state

sealed interface ScreenState {

    data object Loading : ScreenState
    data object Failed : ScreenState
    data class Success(val data: Any) : ScreenState
}