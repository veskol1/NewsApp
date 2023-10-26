package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.api.NewsApi
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.room.ArticleDao
import com.example.newsapp.room.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    private const val BASE_URL = "https://newsapi.org/"

    @Provides
    @Singleton
    fun newsApiServiceProvide(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun newsRepositoryProvide(newApi: NewsApi, articleDao: ArticleDao): NewsRepository {
        return NewsRepository(newApi, articleDao)
    }

    @Provides
    fun provideDao(articleDatabase: ArticleDatabase) : ArticleDao = articleDatabase.articleDao()


    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext appContext: Context): ArticleDatabase {
        return Room.databaseBuilder(appContext,
            ArticleDatabase::class.java, "database name"
        ).build()
    }
}