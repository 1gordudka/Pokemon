package com.igordudka.pokemon.network

import com.igordudka.pokemon.model.PokemonDetail
import com.igordudka.pokemon.model.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {

    @GET("pokemon")
    fun getPokemons() : Call<PokemonResponse>

    @GET("pokemon/" + "{id}")
    fun getPokemonDetail(
        @Path("id") id: String
    ) : Call<PokemonDetail>
}