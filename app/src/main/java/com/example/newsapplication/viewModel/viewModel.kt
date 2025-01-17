package com.example.newsapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import com.example.newsapplication.api.RetrofitInstance
import com.example.newsapplication.data.Article

class NewsViewModel : ViewModel() {
    val newsList = mutableStateOf<List<Article>>(emptyList())
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    fun fetchNews() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val response = RetrofitInstance.api.getNews(
                    apiKey = "pub_65459fa4a970ce8f614838d5cc8156ec3a87c",
                    query = "stock market",
                    country = "in",
                    language = "en"
                    // sentiment = "neutral"
                )
                newsList.value = response.results
            } catch (e: Exception) {
                errorMessage.value = "Failed to load news: ${e.localizedMessage}"
            } finally {
                isLoading.value = false
            }
        }
    }
}
