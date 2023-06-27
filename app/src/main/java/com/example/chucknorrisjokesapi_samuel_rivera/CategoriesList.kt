package com.example.chucknorrisjokesapi_samuel_rivera

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoriesList : AppCompatActivity() {
    private lateinit var categoriesListRecyclerView: RecyclerView
    private lateinit var adapter: CategoriesAdapter
    private var categoryList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories_activity)
        categoriesListRecyclerView = findViewById(R.id.categoriesRecyclerView)
        categoriesListRecyclerView.layoutManager = LinearLayoutManager(this)

        getCategories()
        adapter = CategoriesAdapter(categoryList)
        categoriesListRecyclerView.adapter = adapter
        adapter.submitList(categoryList)
        adapter.onCategoryTap = {
            val intentToJoke = Intent(this, JokeByCategory::class.java)
            intentToJoke.putExtra("category", it)
            startActivity(intentToJoke)
        }
    }

    private fun getCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            val request = fetchData().create(IChuckService::class.java).getCategories("categories")
            val response = request.body()
            runOnUiThread {
                if (response != null) {
                    categoryList.addAll(response)
                    adapter.notifyDataSetChanged()
                }
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