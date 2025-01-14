package com.example.rickymortiprueba

data class EpisodeResponse(
    val info: Info,
    val results: List<Episode>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class Episode(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>
)

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String?,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>
)

data class Location(
    val name: String,
    val url: String
)
