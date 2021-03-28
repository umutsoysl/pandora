package com.laks.tvseries.core.data.category

import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.PersonModel
import com.laks.tvseries.core.data.model.TvSeriesListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryApi {

    @GET("/3/trending/{media_type}/{time_window}")
    suspend fun getTrending(@Path("media_type") type: String, @Path("time_window") time: String, @Query("api_key") authToken: String,): Response<DiscoverMovieListModel>

    @GET("/3/movie/now_playing")
    suspend fun getNowPlaying(@Query("api_key") authToken: String, @Query("page") page: Int, @Query("language") language: String): Response<DiscoverMovieListModel>

    @GET("/3/tv/popular")
    suspend fun getPopularTVShows(@Query("api_key") authToken: String, @Query("page") page: Int, @Query("language") language: String): Response<DiscoverMovieListModel>

    @GET("/3/person/popular")
    suspend fun getPopularPeople(@Query("api_key") authToken: String, @Query("page") page: Int, @Query("language") language: String): Response<PersonModel>

    @GET("/3/movie/upcoming")
    suspend fun getUpComingMovie(@Query("api_key") authToken: String, @Query("page") page: Int, @Query("language") language: String): Response<DiscoverMovieListModel>

}