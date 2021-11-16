package dev.bahodir.skipadsapp.retrofit.gnews

import dev.bahodir.skipadsapp.gnews.Gnews
import dev.bahodir.skipadsapp.news.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET

interface GnewsService {
    @GET("search?q=example&token=38f0eba9df77ce7da6480e73d81fa026")
    fun getNews() : Call<Gnews>
}