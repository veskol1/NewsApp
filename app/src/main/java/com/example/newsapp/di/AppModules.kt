package com.example.newsapp.di

import com.example.newsapp.api.NewsApi
import com.example.newsapp.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun newsRepositoryProvide(newApi: NewsApi): NewsRepository {
        return NewsRepository(newApi)
    }

}