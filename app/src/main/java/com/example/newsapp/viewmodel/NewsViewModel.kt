package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Article
import com.example.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
    private val _uiState =
        MutableStateFlow(UiState(status = Status.LOADING, articles = arrayListOf()))
    var uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        fetchingData()
    }

    private fun fetchingData() {

        _uiState.update { uiState ->
            uiState.copy(
                status = Status.LOADING
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            updateStateStatus(repository.fetchData())
            updateStateArticlesData()
        }
    }

    private fun updateStateArticlesData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getArticles().collectLatest { articles ->
                if (articles.isNotEmpty()) {
                    _uiState.update { uiState ->
                        uiState.copy(
                            articles = articles,
                            status = Status.DONE
                        )
                    }
                }
            }
        }
    }

    private fun updateStateStatus(status: Status) {
        _uiState.update { uiState ->
            uiState.copy(
                status = status
            )
        }
    }

    fun updateNewArticles() {
        fetchingData()
    }

    fun getArticle(pos: Int): Article? {
        return uiState.value.articles?.get(pos)
    }
}

data class UiState(
    val status: Status,
    val articles: List<Article>?
)

enum class Status {
    LOADING,
    ERROR,
    DONE
}