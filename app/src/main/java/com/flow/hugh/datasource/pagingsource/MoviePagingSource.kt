package com.flow.hugh.datasource.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.flow.hugh.Const
import com.flow.hugh.data.Movie
import com.flow.hugh.data.SearchOption
import com.flow.hugh.datasource.api.MovieApi
import java.io.IOException

class MoviePagingSource(
    private val api: MovieApi,
    private val searchOption: SearchOption,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: Const.INITIAL_PAGE_NUMBER

        return try {
            val response = api.getMovieList(
                query = searchOption.query,
                display = Const.LOAD_SIZE,
                start = page,
                genre = searchOption.genre,
                country = searchOption.country,
                yearFrom = searchOption.yearFrom,
                yearTo = searchOption.yearTo,
            )

            val items = response.body()?.items?.map { it.toMovie() } ?: emptyList()

            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - Const.LOAD_SIZE,
                nextKey = if (items.size < Const.LOAD_SIZE) { null } else { page + Const.LOAD_SIZE }
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int?
        = state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
}