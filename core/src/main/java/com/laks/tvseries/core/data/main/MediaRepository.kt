package com.laks.tvseries.core.data.main

import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.data.model.*
import kotlinx.coroutines.flow.Flow

class MediaRepository: BaseRepository<ScheduleApi>(ScheduleApi::class.java) {

    fun getDiscoverMoviesList(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getDiscoverMoviesList( page = requestModel.page!!)
        }
    }

    fun getDiscoverTvList(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getDiscoverTvList(page = requestModel.page!!)
        }
    }

    fun getMovieDetail(requestModel: GlobalRequestModel): Flow<MovieDetailModel?> {
        return fetchData {
            api.getMovieDetail(movieID = requestModel.movieID!!)
        }
    }

    fun getTVDetail(requestModel: GlobalRequestModel): Flow<MovieDetailModel?> {
        return fetchData {
            api.getTvDetail(tvID = requestModel.movieID!!)
        }
    }

    fun getMovieVideo(requestModel: GlobalRequestModel): Flow<VideoModel?> {
        return fetchData(isLoadingShown = true) {
            api.getMovieVideo(movieID = requestModel.movieID!!)
        }
    }

    fun getTvVideo(requestModel: GlobalRequestModel): Flow<VideoModel?> {
        return fetchData(isLoadingShown = true) {
            api.getTvVideo(tvID = requestModel.movieID!!)
        }
    }

    fun getMovieCredits(requestModel: GlobalRequestModel): Flow<MovieCreditsModel?> {
        return fetchData(isLoadingShown = true) {
            api.getMovieCredit( movieID = requestModel.movieID!!)
        }
    }

    fun getTvCredits(requestModel: GlobalRequestModel): Flow<MovieCreditsModel?> {
        return fetchData(isLoadingShown = true) {
            api.getTvCredit(tvID = requestModel.movieID!!)
        }
    }

    fun getMovieRecommendations(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getMovieRecommendations(movieID = requestModel.movieID!!, page = requestModel.page!!)
        }
    }

    fun getTvRecommendations(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getTvRecommendations(tvID = requestModel.movieID!!, page = requestModel.page!!)
        }
    }

    fun getMovieImage(requestModel: GlobalRequestModel): Flow<MediaImageModel?> {
        return fetchData(isLoadingShown = true) {
            api.getMovieImage( movieID = requestModel.movieID!!)
        }
    }

    fun getTvImage(requestModel: GlobalRequestModel): Flow<MediaImageModel?> {
        return fetchData(isLoadingShown = true) {
            api.getTvImage(tvID = requestModel.movieID!!)
        }
    }
}