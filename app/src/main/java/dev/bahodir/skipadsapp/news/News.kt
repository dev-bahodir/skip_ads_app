package dev.bahodir.skipadsapp.news

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)