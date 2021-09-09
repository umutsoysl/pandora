package com.laks.tvseries.core.data.main

import com.laks.tvseries.core.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleApi {
    @GET("/3/discover/movie")
    suspend fun getDiscoverMoviesList(@Query("page") page: Int, @Query("sort_by") sortBy: String, @Query("with_genres") genre: String, @Query("vote_average.gte") minVote: Double,  @Query("vote_average.lte") maxVote: Double, @Query("primary_release_date.gte") minYear: String,  @Query("primary_release_date.lte") maxYear: String): Response<DiscoverMovieListModel>

    @GET("/3/discover/tv")
    suspend fun getDiscoverTvList(@Query("page") page: Int, @Query("sort_by") sortBy: String, @Query("with_genres") genre: String, @Query("vote_average.gte") minVote: Double,  @Query("vote_average.lte") maxVote: Double, @Query("primary_release_date.gte") minYear: String,  @Query("primary_release_date.lte") maxYear: String): Response<DiscoverMovieListModel>

    @GET("/3/movie/{movieID}")
    suspend fun getMovieDetail(@Path("movieID") movieID: String): Response<MovieDetailModel>

    @GET("/3/tv/{tvID}")
    suspend fun getTvDetail(@Path("tvID") tvID: String): Response<MovieDetailModel>

    @GET("/3/movie/{movieID}/videos")
    suspend fun getMovieVideo(@Path("movieID") movieID: String, @Query("language") language: String): Response<VideoModel>

    @GET("/3/tv/{tvID}/videos")
    suspend fun getTvVideo(@Path("tvID") tvID: String, @Query("language") language: String): Response<VideoModel>

    @GET("/3/movie/{movieID}/credits")
    suspend fun getMovieCredit(@Path("movieID") movieID: String): Response<MovieCreditsModel>

    @GET("/3/tv/{tvID}/credits")
    suspend fun getTvCredit(@Path("tvID") tvID: String): Response<MovieCreditsModel>

    @GET("/3/movie/{movieID}/recommendations")
    suspend fun getMovieRecommendations(@Path("movieID") movieID: String, @Query("page") page: Int): Response<DiscoverMovieListModel>

    @GET("/3/tv/{tvID}/recommendations")
    suspend fun getTvRecommendations(@Path("tvID") tvID: String, @Query("page") page: Int): Response<DiscoverMovieListModel>

    @GET("/3/movie/{movieID}/images")
    suspend fun getMovieImage(@Path("movieID") movieID: String): Response<MediaImageModel>

    @GET("/3/tv/{tvID}/images")
    suspend fun getTvImage(@Path("tvID") tvID: String): Response<MediaImageModel>

    @GET("/3/genre/movie/list")
    suspend fun getMovieGenres(): Response<GenreListModel>

    @GET("/3/genre/tv/list")
    suspend fun getTvGenres(): Response<GenreListModel>

}