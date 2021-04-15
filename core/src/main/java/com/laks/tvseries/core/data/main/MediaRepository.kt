package com.laks.tvseries.core.data.main

import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.data.model.*
import kotlinx.coroutines.flow.Flow

class MediaRepository: BaseRepository<ScheduleApi>(ScheduleApi::class.java) {

    fun getDiscoverMoviesList(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getDiscoverMoviesList(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getDiscoverTvList(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getDiscoverTvList(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getMovieDetail(requestModel: GlobalRequestModel): Flow<MovieDetailModel?> {
        return fetchData {
            api.getMovieDetail(authToken = requestModel.apiKey!!, movieID = requestModel.movieID!!, language = requestModel.language!!)
        }
    }

    fun getTVDetail(requestModel: GlobalRequestModel): Flow<MovieDetailModel?> {
        return fetchData {
            api.getTvDetail(authToken = requestModel.apiKey!!, tvID = requestModel.movieID!!, language = requestModel.language!!)
        }
    }

    fun getMovieVideo(requestModel: GlobalRequestModel): Flow<VideoModel?> {
        return fetchData(isLoadingShown = true) {
            api.getMovieVideo(authToken = requestModel.apiKey!!, movieID = requestModel.movieID!!, language = requestModel.language!!)
        }
    }

    fun getTvVideo(requestModel: GlobalRequestModel): Flow<VideoModel?> {
        return fetchData(isLoadingShown = true) {
            api.getTvVideo(authToken = requestModel.apiKey!!, tvID = requestModel.movieID!!, language = requestModel.language!!)
        }
    }

    fun getMovieCredits(requestModel: GlobalRequestModel): Flow<MovieCreditsModel?> {
        return fetchData(isLoadingShown = true) {
            api.getMovieCredit(authToken = requestModel.apiKey!!, movieID = requestModel.movieID!!, language = requestModel.language!!)
        }
    }

    fun getTvCredits(requestModel: GlobalRequestModel): Flow<MovieCreditsModel?> {
        return fetchData(isLoadingShown = true) {
            api.getTvCredit(authToken = requestModel.apiKey!!, tvID = requestModel.movieID!!, language = requestModel.language!!)
        }
    }

    fun getMovieRecommendations(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getMovieRecommendations(authToken = requestModel.apiKey!!, movieID = requestModel.movieID!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getTvRecommendations(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getTvRecommendations(authToken = requestModel.apiKey!!, tvID = requestModel.movieID!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getMovieImage(requestModel: GlobalRequestModel): Flow<MediaImageModel?> {
        return fetchData(isLoadingShown = true) {
            api.getMovieImage(authToken = requestModel.apiKey!!, movieID = requestModel.movieID!!)
        }
    }

    fun getTvImage(requestModel: GlobalRequestModel): Flow<MediaImageModel?> {
        return fetchData(isLoadingShown = true) {
            api.getTvImage(authToken = requestModel.apiKey!!, tvID = requestModel.movieID!!)
        }
    }
}