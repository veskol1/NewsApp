package com.example.newsapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NewsArticles(
    val articles: ArrayList<Article>
)

@Parcelize
data class Article(
    @SerializedName("urlToImage")
    val image: String?,

    @SerializedName("author")
    val name: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val desc: String?,

    @SerializedName("publishedAt")
    val date: String?
) : Parcelable
