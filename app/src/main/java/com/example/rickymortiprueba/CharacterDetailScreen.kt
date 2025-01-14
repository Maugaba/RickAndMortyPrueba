package com.example.rickymortiprueba

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun CharacterDetailScreen(characterId: Int, onEpisodeClick: (Int) -> Unit) {
    val api = ApiClient.api
    var character by remember { mutableStateOf<Character?>(null) }

    LaunchedEffect(characterId) {
        try {
            character = api.getCharacter(characterId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    character?.let { characterDetails ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colors.onSurface.copy(alpha = 0.1f))
            ) {
                Image(
                    painter = rememberImagePainter(characterDetails.image),
                    contentDescription = characterDetails.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = characterDetails.name,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colors.primary
            )
            DetailRowCharacter(label = "Estado:", value = characterDetails.status)
            DetailRowCharacter(label = "Especie:", value = characterDetails.species)
            DetailRowCharacter(label = "Género:", value = characterDetails.gender)

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Apariciones en episodios:",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn {
                items(characterDetails.episode) { episodeUrl ->
                    val episodeId = episodeUrl.split("/").last().toInt()
                    EpisodeItem(
                        episodeId = episodeId,
                        onClick = { onEpisodeClick(episodeId) }
                    )
                }
            }
        }
    }
}

@Composable
fun DetailRowCharacter(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colors.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun EpisodeItem(episodeId: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.episodes),
                    contentDescription = "Imagen del episodio por defecto",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Episodio $episodeId",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}
