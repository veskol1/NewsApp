package com.example.newsapp.viewmodel

import com.example.newsapp.model.Article

class NewsViewModel {


}

data class UiState(
    val articles: ArrayList<Article>?,
    val stateStatus: Status
)

enum class Status {
    LOADING,
    ERROR,
    DONE
}