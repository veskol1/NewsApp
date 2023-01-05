package com.example.newsapp.repository

import com.example.newsapp.api.NewsApi
import com.example.newsapp.model.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newApi: NewsApi) {
    suspend fun getArticles(): ArrayList<Article>? {
        val apiResponse = newApi.getArticles()
        return if (apiResponse.isSuccessful.not() || apiResponse.body() == null)
            null
        else
            apiResponse.body()?.articles
    }
}