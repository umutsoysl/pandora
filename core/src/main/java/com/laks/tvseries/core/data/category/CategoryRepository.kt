package com.laks.tvseries.core.data.category

import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.MovieRequestModel
import com.laks.tvseries.core.data.model.PersonModel
import com.laks.tvseries.core.data.model.TvSeriesListModel
import kotlinx.coroutines.flow.Flow

class CategoryRepository: BaseRepository<CategoryApi>(CategoryApi::class.java) {

    fun getTrending(requestModel: MovieRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getTrending(authToken = requestModel.apiKey!!, type = requestModel.type!!, time = requestModel.time!!)
        }
    }

    fun getNowPlaying(requestModel: MovieRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getNowPlaying(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getTvPopularShows(requestModel: MovieRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getPopularTVShows(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getPopularPeople(requestModel: MovieRequestModel): Flow<PersonModel?> {
        return fetchData(isLoadingShown = true) {
            api.getPopularPeople(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getUpComingMovie(requestModel: MovieRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getUpComingMovie(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getMoviePopular(requestModel: MovieRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getPopularMovie(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }
}