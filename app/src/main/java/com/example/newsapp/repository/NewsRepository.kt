package com.example.newsapp.repository

import android.util.Log
import com.example.newsapp.api.NewsApi
import com.example.newsapp.model.Article
import com.example.newsapp.room.ArticleDao
import com.example.newsapp.viewmodel.Status
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.Exception

class NewsRepository @Inject constructor(private val newsApi: NewsApi, private val articleDao: ArticleDao) {

    suspend fun fetchData(): Status {
        return try {
            val apiResponse = newsApi.getArticles()
            if (apiResponse.isSuccessful && apiResponse.body() != null) {
                val articles = apiResponse.body()!!.articles
                val savedArticles = getArticlesNotFlow()

                if (savedArticles.isNotEmpty()) {
                    if (articles != savedArticles) {
                        Log.d("haha","delete all they are not equal")
                        articleDao.deleteAll()
                        articleDao.insertAll(articles)
                    }
                } else {
                    articleDao.insertAll(articles)
                }

                Status.DONE
            } else {
                Status.ERROR
            }
        } catch (e: Exception) { //if no internet connection , check database first
            Status.ERROR
        }
    }

    fun getArticles(): Flow<List<Article>> {
        return articleDao.getAll()
    }

    private fun getArticlesNotFlow(): List<Article> {
        return articleDao.getAllNotFlow()
    }
}