package com.igordudka.pokemon.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

const val LIST = "list"
const val DETAIl = "detail"

@Composable
fun App() {

    var navController = rememberNavController()
    var detailId by remember {
        mutableStateOf("")
    }

    NavHost(navController = navController, startDestination = LIST){
        composable(LIST){
            ListScreen(goToDetail = {
                val regex = Regex("pokemon/(\\d+)/")
                val matchResult = regex.find(it)
                val pokemonNumber = matchResult?.groupValues?.get(1)
                detailId = pokemonNumber.toString()
                navController.navigate(DETAIl)
            })
        }
        composable(DETAIl){
            BackHandler {
                navController.navigate(LIST)
            }
            DetailScreen(id = detailId)
        }
    }
}