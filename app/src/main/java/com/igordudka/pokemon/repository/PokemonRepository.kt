package com.igordudka.pokemon.repository

import com.igordudka.pokemon.network.PokemonApi

class PokemonRepository(
    private val pokemonApi: PokemonApi
) {

    fun getPokemons() = pokemonApi.getPokemons()

    fun getDetailPokemon(id: String) = pokemonApi.getPokemonDetail(id)
}