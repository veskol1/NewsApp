package com.example.newsapp.api

import com.example.newsapp.model.NewsArticles
import retrofit2.Response
import retrofit2.http.GET

interface NewsApi {
    @GET("/v2/top-headlines?country=us&apiKey=f5595460a6484db1a853fe18b954f6fd")
    suspend fun getArticles(): Response<NewsArticles>
}