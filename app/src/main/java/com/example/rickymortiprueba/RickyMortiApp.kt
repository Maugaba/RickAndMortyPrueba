package com.example.rickymortiprueba

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun RickAndMortyApp() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "episodes") {
        composable("episodes") {
            EpisodeListScreen { episodeId ->
                navController.navigate("episodeDetail/$episodeId")
            }
        }
        composable("episodeDetail/{episodeId}") { backStackEntry ->
            val episodeId = backStackEntry.arguments?.getString("episodeId")?.toInt() ?: 0
            EpisodeDetailScreen(episodeId) { characterId ->
                navController.navigate("characterDetail/$characterId")
            }
        }
        composable("characterDetail/{characterId}") { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId")?.toInt() ?: 0
            CharacterDetailScreen(characterId) { episodeId ->
                navController.navigate("episodeDetail/$episodeId")
            }
        }
    }
}
