package com.flow.hugh.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.flow.hugh.data.Movie
import com.flow.hugh.data.Recent
import com.flow.hugh.data.SearchOption
import com.flow.hugh.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    var movieFlowList: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(PagingData.empty())
    val recentLiveList: LiveData<List<String>> = repository.getRecentLiveList()
    val searchOptionLiveData = MutableLiveData<SearchOption>()
    val recentClickLiveData = MutableLiveData<String>()

    fun search(searchOption: SearchOption) {
        if (searchOption.query.isEmpty()) return

        viewModelScope.launch(Dispatchers.Main) {
            searchOptionLiveData.value = searchOption
            addRecent(searchOption.query)
            repository.getMovieFlowList(searchOption).cachedIn(viewModelScope).collect {
                movieFlowList.value = it
            }
        }
    }

    fun search(query: String) {
        if (query.isEmpty()) return

        viewModelScope.launch(Dispatchers.Main) {
            searchOptionLiveData.value = SearchOption(query = query)
            addRecent(query)
            repository.getMovieFlowList(SearchOption(query = query)).cachedIn(viewModelScope).collect {
                movieFlowList.value = it
            }
        }
    }

    private fun addRecent(query: String) {
        if (query.isEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRecent(Recent(0, query))
        }
    }

    fun removeRecent(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeRecent(query)
        }
    }

    fun removeAllRecent() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeAllRecent()
        }
    }
}