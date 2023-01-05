package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newsapp.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewsViewModel: ViewModel() {
    private val _uiState =
        MutableStateFlow(UiState(status = Status.LOADING, articles = arrayListOf()))
    var uiState: StateFlow<UiState> = _uiState.asStateFlow()
}

data class UiState(
    val status: Status,
    val articles: ArrayList<Article>?
)

enum class Status {
    LOADING,
    ERROR,
    DONE
}