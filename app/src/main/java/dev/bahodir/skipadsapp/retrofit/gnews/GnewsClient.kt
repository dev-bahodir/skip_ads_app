package dev.bahodir.skipadsapp.retrofit.gnews

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GnewsClient {
    private const val BASE_URL = "https://gnews.io/api/v4/"

    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    var apiGnewsService = getRetrofit().create(GnewsService::class.java)
}