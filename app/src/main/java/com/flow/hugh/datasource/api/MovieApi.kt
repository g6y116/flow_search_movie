package com.flow.hugh.datasource.api

import com.flow.hugh.data.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    // need : query. yearFrom, yearTo은 함께 사용 해야함.
    @GET("movie.json")
    suspend fun getMovieList(
        @Query("query") query: String,
        @Query("display") display: Int?,
        @Query("start") start: Int?,
        @Query("genre") genre: String?,
        @Query("country") country: String?,
        @Query("yearfrom") yearFrom: Int?,
        @Query("yearto") yearTo: Int?,
    ) : Response<MovieListResponse>
}