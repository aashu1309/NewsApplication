package com.example.newsapplication.api

import com.example.newsapplication.data.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {
    @GET("latest") // Endpoint

    // Type of Query
    suspend  fun getNews(
        @Query("apikey") apiKey: String,
        @Query("q") query: String,
        @Query("country") country : String,
        @Query("language") language : String
        // @Query("sentiment") sentiment : String,
    ): NewsResponse
}