package com.example.chucknorrisjokesapi_samuel_rivera

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface IChuckService {
    @GET
    suspend fun getRandomJoke(@Url url: String): Response<Joke>
    @GET
    suspend fun getCategories(@Url url: String): Response<List<String>>
    @GET
    suspend fun getJokeByCategory(@Url url: String): Response<Joke>
}