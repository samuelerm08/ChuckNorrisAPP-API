package com.example.chucknorrisjokesapi_samuel_rivera

import com.google.gson.annotations.SerializedName

data class Joke (
    @SerializedName("value") val joke: String,
)