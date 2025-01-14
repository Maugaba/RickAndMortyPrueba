package com.example.rickymortiprueba

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("episode")
    suspend fun getEpisodes(@Query("page") page: Int): EpisodeResponse

    @GET("episode/{id}")
    suspend fun getEpisode(@Path("id") id: Int): Episode

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character
}

object ApiClient {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    val api: RickAndMortyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RickAndMortyApi::class.java)
}

