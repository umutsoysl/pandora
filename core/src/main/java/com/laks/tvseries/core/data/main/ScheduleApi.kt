package com.laks.tvseries.core.data.main

import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.MovieDetailModel
import com.laks.tvseries.core.data.model.VideoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleApi {
    @GET("/3/discover/movie")
    suspend fun getDiscoverMoviesList(@Query("api_key") authToken: String, @Query("page") page: Int, @Query("language") language: String): Response<DiscoverMovieListModel>

    @GET("/3/movie/{movieID}")
    suspend fun getMovieDetail(@Path("movieID") movieID: String, @Query("api_key") authToken: String, @Query("language") language: String): Response<MovieDetailModel>

    @GET("/3/tv/{tvID}")
    suspend fun getTvDetail(@Path("tvID") tvID: String, @Query("api_key") authToken: String, @Query("language") language: String): Response<MovieDetailModel>

    @GET("/3/movie/{movieID}/videos")
    suspend fun getMovieVideo(@Path("movieID") movieID: String, @Query("api_key") authToken: String, @Query("language") language: String): Response<VideoModel>

    @GET("/3/tv/{tvID}/videos")
    suspend fun getTvVideo(@Path("tvID") tvID: String, @Query("api_key") authToken: String, @Query("language") language: String): Response<VideoModel>

}