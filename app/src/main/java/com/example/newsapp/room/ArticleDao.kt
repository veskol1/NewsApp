package com.example.newsapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * from articles_table")
    fun getAll(): Flow<List<Article>>

    @Query("SELECT * from articles_table")
    fun getAllNotFlow(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<Article>)

    @Query("DELETE from articles_table")
    fun deleteAll()
}