package com.example.chucknorrisjokesapi_samuel_rivera

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JokeByCategory : AppCompatActivity() {
    private lateinit var jokeOnDetail: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_detail)
        jokeOnDetail = findViewById(R.id.detailJokeTitle)
        val bundle = intent.extras
        val category = bundle?.getString("category", "")
        getJokeByCategory(category.toString())
    }

    private fun getJokeByCategory(category: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = fetchData().create(IChuckService::class.java)
                .getJokeByCategory("random?category=${category}")
            val response = request.body()
            try {
                runOnUiThread {
                    if (request.code() == 200) {
                        val jokeValue = response?.joke
                        if (jokeValue != null) jokeOnDetail.text = jokeValue.toString()
                    }
                }
            } catch (e: Exception) {
                throw Exception(e)
            }
        }
    }

    private fun fetchData(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/jokes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}