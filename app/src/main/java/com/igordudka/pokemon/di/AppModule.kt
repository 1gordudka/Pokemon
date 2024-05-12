package com.igordudka.pokemon.di

import com.google.gson.GsonBuilder
import com.igordudka.pokemon.network.PokemonApi
import com.igordudka.pokemon.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providePokemonRepo() : PokemonRepository{
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val api = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PokemonApi::class.java)
        return PokemonRepository(api)
    }
}