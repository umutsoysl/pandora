package com.laks.tvseries.core.data.main

import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleApi {
    @GET("/3/discover/movie")
    suspend fun getDiscoverMoviesList(@Query("api_key") authToken: String, @Query("page") page: Int, @Query("language") language: String): Response<DiscoverMovieListModel>

    @POST("/3/movie/{movieID}")
    suspend fun getDiscoverMoviesDetail(@Query("api_key") authToken: String, @Query("language") language: String, @Path("movieID") movieID: Int): Response<MovieModel>
}