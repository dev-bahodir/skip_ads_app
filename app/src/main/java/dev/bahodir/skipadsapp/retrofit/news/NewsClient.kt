package dev.bahodir.skipadsapp.retrofit.news

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsClient {
    private const val BASE_URL = "https://newsapi.org/"

    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    var apiNewsService = getRetrofit().create(NewsService::class.java)
}