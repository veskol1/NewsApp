package com.example.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Article
import com.example.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
    private val _uiState =
        MutableStateFlow(UiState(status = Status.LOADING, articles = arrayListOf()))
    var uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        updateState()
    }

    private fun updateState() {
        viewModelScope.launch(Dispatchers.IO) {
            val articles = repository.getArticles()
            if (articles != null) {
                _uiState.emit(
                    UiState(
                        status = Status.DONE,
                        articles = articles
                    )
                )
            } else {
                _uiState.update {
                    it.copy(
                        status = Status.ERROR
                    )
                }
            }
        }
    }
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