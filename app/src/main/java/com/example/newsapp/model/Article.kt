package com.example.newsapp.model

data class NewsArticles(
    val articles: ArrayList<Article>
)

data class Article(
    val image: String,
    val name: String,
    val title: String,
    val desc: String,
    val date: String,
)
