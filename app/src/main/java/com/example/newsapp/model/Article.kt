package com.example.newsapp.model

import com.google.gson.annotations.SerializedName

data class NewsArticles(
    val articles: ArrayList<Article>
)

data class Article(
    @SerializedName("urlToImage")
    val image: String,

    @SerializedName("author")
    val name: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val desc: String,

    @SerializedName("publishedAt")
    val date: String,

)
