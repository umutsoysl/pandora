package com.laks.tvseries.core.data

import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.MovieModel
import com.laks.tvseries.core.data.model.MovieRequestModel
import kotlinx.coroutines.flow.Flow

class ScheduleRepository: BaseRepository<ScheduleApi>(ScheduleApi::class.java) {

    fun getDiscoverMoviesList(requestModel: MovieRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getDiscoverMoviesList(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getDiscoverMoviesDetail(requestModel: MovieRequestModel): Flow<MovieModel?> {
        return fetchData {
            api.getDiscoverMoviesDetail(authToken = requestModel.apiKey!!, movieID = requestModel.movieID!!, language = requestModel.language!!)
        }
    }
}