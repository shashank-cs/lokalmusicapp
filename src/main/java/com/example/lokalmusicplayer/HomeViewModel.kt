
package com.example.lokalmusicplayer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    var songs = mutableStateOf<List<Song>>(emptyList())
        private set

    var isLoading = mutableStateOf(false)
        private set

    var errorMsg = mutableStateOf("")
        private set

    init {
        searchSongs("arijit")
    }

    fun searchSongs(query: String) {
        viewModelScope.launch {
            isLoading.value = true
            errorMsg.value = ""
            try {
                val response = RetrofitClient.api.searchSongs(query)
                songs.value = response.data.results
            } catch (e: Exception) {
                errorMsg.value = "Failed to load songs"
            } finally {
                isLoading.value = false
            }
        }
    }
}
