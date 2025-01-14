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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun EpisodeDetailScreen(episodeId: Int, onCharacterClick: (Int) -> Unit) {
    val api = ApiClient.api
    var episode by remember { mutableStateOf<Episode?>(null) }

    LaunchedEffect(episodeId) {
        try {
            episode = api.getEpisode(episodeId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    episode?.let { episodeDetails ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = episodeDetails.name,
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colors.primary.copy(alpha = 0.1f)),
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    DetailRow(label = "Fecha de emisión:", value = episodeDetails.air_date)
                    DetailRow(label = "Código:", value = episodeDetails.episode)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Personajes:",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(episodeDetails.characters) { characterUrl ->
                    val characterId = characterUrl.split("/").last().toInt()

                    CharacterItem(
                        characterId = characterId,
                        onClick = { onCharacterClick(characterId) }
                    )
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
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
fun CharacterItem(characterId: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = 2.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.surface)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.characters),
                    contentDescription = "Imagen del personaje por defecto",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Personaje $characterId",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

    }
}
