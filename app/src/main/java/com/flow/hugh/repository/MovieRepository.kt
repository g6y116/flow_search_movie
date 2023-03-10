package com.flow.hugh.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.flow.hugh.Const
import com.flow.hugh.data.Movie
import com.flow.hugh.data.Recent
import com.flow.hugh.data.SearchOption
import com.flow.hugh.datasource.api.MovieApi
import com.flow.hugh.datasource.dao.RecentDao
import com.flow.hugh.datasource.pagingsource.MoviePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MovieRepository {
    fun getMovieFlowList(searchOption: SearchOption): Flow<PagingData<Movie>>
    fun getRecentLiveList(): LiveData<List<String>>
    suspend fun addRecent(recent: Recent)
    suspend fun removeRecent(query: String)
    suspend fun removeAllRecent()
}

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val dao: RecentDao,
) : MovieRepository {

    override fun getMovieFlowList(searchOption: SearchOption): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = Const.LOAD_SIZE, enablePlaceholders = true),
            pagingSourceFactory = { MoviePagingSource(api, searchOption) },
        ).flow
    }

    override fun getRecentLiveList(): LiveData<List<String>> {
        return dao.getLiveList()
    }

    override suspend fun addRecent(recent: Recent) {
        return dao.add(recent)
    }

    override suspend fun removeRecent(query: String) {
        return dao.remove(query)
    }

    override suspend fun removeAllRecent() {
        return dao.removeAll()
    }
}