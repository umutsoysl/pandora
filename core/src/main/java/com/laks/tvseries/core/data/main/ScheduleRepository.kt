package com.laks.tvseries.core.data.main

import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.MovieDetailModel
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.data.model.MovieRequestModel
import kotlinx.coroutines.flow.Flow

class ScheduleRepository: BaseRepository<ScheduleApi>(ScheduleApi::class.java) {

    fun getDiscoverMoviesList(requestModel: MovieRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getDiscoverMoviesList(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getMovieDetail(requestModel: MovieRequestModel): Flow<MovieDetailModel?> {
        return fetchData(isLoadingShown = false) {
            api.getMovieDetail(authToken = requestModel.apiKey!!, movieID = requestModel.movieID!!, language = requestModel.language!!)
        }
    }

    fun getTVDetail(requestModel: MovieRequestModel): Flow<MovieDetailModel?> {
        return fetchData(isLoadingShown = false) {
            api.getTvDetail(authToken = requestModel.apiKey!!, tvID = requestModel.movieID!!, language = requestModel.language!!)
        }
    }
}