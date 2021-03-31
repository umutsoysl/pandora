package com.laks.tvseries.core.data.main

import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.data.model.*
import kotlinx.coroutines.flow.Flow

class ScheduleRepository: BaseRepository<ScheduleApi>(ScheduleApi::class.java) {

    fun getDiscoverMoviesList(requestModel: MovieRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getDiscoverMoviesList(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getMovieDetail(requestModel: MovieRequestModel): Flow<MovieDetailModel?> {
        return fetchData {
            api.getMovieDetail(authToken = requestModel.apiKey!!, movieID = requestModel.movieID!!, language = requestModel.language!!)
        }
    }

    fun getTVDetail(requestModel: MovieRequestModel): Flow<MovieDetailModel?> {
        return fetchData {
            api.getTvDetail(authToken = requestModel.apiKey!!, tvID = requestModel.movieID!!, language = requestModel.language!!)
        }
    }

    fun getMovieVideo(requestModel: MovieRequestModel): Flow<VideoModel?> {
        return fetchData {
            api.getMovieVideo(authToken = requestModel.apiKey!!, movieID = requestModel.movieID!!, language = requestModel.language!!)
        }
    }

    fun getTvVideo(requestModel: MovieRequestModel): Flow<VideoModel?> {
        return fetchData {
            api.getTvVideo(authToken = requestModel.apiKey!!, tvID = requestModel.movieID!!, language = requestModel.language!!)
        }
    }
}