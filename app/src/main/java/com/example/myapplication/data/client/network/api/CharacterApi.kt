package com.example.myapplication.data.source.remote.api

import com.example.myapplication.data.model.ReponseApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    //GET CHARACTERS
    @GET("api/character")
    suspend fun getCharacters(@Query("page") page: Int): Response<ReponseApiModel>
}