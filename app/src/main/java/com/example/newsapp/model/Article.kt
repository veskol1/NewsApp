package com.example.newsapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NewsArticles(
    val articles: List<Article>
)

@Entity(tableName = "articles_table")
@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerializedName("urlToImage")
    val image: String? = "",

    @SerializedName("author")
    val name: String? = "",

    @SerializedName("title")
    val title: String? = "",

    @SerializedName("description")
    val desc: String? = "",

    @SerializedName("publishedAt")
    val date: String? = ""
) : Parcelable {


    override fun equals(other: Any?): Boolean {
        return (other is Article) && (name == other.name) && (title == other.title)
    }
}


