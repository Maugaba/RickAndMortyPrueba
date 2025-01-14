package com.example.rickymortiprueba

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun EpisodeListScreen(onEpisodeClick: (Int) -> Unit) {
    val api = ApiClient.api
    val episodes = remember { mutableStateListOf<Episode>() }
    var currentPage by remember { mutableStateOf(1) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(currentPage) {
        isLoading = true
        try {
            val response = api.getEpisodes(currentPage)
            episodes.addAll(response.results)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        isLoading = false
    }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Episodios de Rick y Morty",
                style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onPrimary)
            )
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(episodes) { episode ->
                EpisodeItem(episode) {
                    onEpisodeClick(episode.id)
                }
            }
            if (isLoading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
        Button(
            onClick = { currentPage += 1 },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary
            )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDownward,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cargar Más")
        }
    }
}

@Composable
fun EpisodeItem(episode: Episode, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(MaterialTheme.colors.surface)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.episodes),
                    contentDescription = "Imagen del episodio por defecto",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = episode.name, style = MaterialTheme.typography.h6)
                Text(
                    text = "Emitido el: ${episode.air_date}",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondary
                )
                Text(
                    text = "Código: ${episode.episode}",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

