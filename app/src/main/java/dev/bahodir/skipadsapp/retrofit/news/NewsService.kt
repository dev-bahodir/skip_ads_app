package dev.bahodir.skipadsapp.retrofit.news

import dev.bahodir.skipadsapp.news.News
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("v2/everything?q=tesla&from=2021-10-14&sortBy=publishedAt&apiKey=ca2e522a39ac49c088fe6438c1b9a1fe")
    fun getNews() : Call<News>
}