package com.example.newsapplication.data

// Defining Data Model of the response
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val results: List<Article>
)

data class Article(
    val title: String,
    val link: String,
    val description: String?,
    val pubDate: String?,
    val sourceId: String?,
    var category: List<String>?,
    val image_url : String?
)