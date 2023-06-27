package com.example.chucknorrisjokesapi_samuel_rivera

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomJokeActivity : AppCompatActivity() {
    private lateinit var randomJokeResult: TextView
    private lateinit var generateRandomJokeButton: Button
    private lateinit var goToCategoriesButton: Button
    private var categoryList = mutableListOf<String>()
    private lateinit var adapter: CategoriesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.random_joke_activity)

        randomJokeResult = findViewById(R.id.randomJokeResult)
        generateRandomJokeButton = findViewById(R.id.generateRandomJokeButton)
        goToCategoriesButton = findViewById(R.id.goToCategoriesButton)

        getRandomJoke()
        goToCategoriesButton.setOnClickListener {
            goToCategories()
        }
    }

    private fun getRandomJoke() {
        generateRandomJokeButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val request = fetchData().create(IChuckService::class.java).getRandomJoke("random")
                val response = request.body()
                try {
                    runOnUiThread {
                        if (request.isSuccessful) {
                            val jokeValue = response?.joke
                            if (jokeValue != null) randomJokeResult.text = jokeValue.toString()
                        }
                    }
                } catch (e: Exception) {
                    throw Exception(e)
                }
            }
        }
    }

    private fun goToCategories() {
        val intentToCategories = Intent(this, CategoriesList::class.java)
        startActivity(intentToCategories)
    }

    private fun fetchData(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/jokes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
