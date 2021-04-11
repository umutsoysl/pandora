package com.laks.tvseries.core.data.category

import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.GlobalRequestModel
import com.laks.tvseries.core.data.model.PersonModel
import kotlinx.coroutines.flow.Flow

class CategoryRepository: BaseRepository<CategoryApi>(CategoryApi::class.java) {

    fun getTrending(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getTrending(authToken = requestModel.apiKey!!, type = requestModel.type!!, time = requestModel.time!!)
        }
    }

    fun getNowPlaying(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getNowPlaying(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getTvPopularShows(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getPopularTVShows(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getPopularPeople(requestModel: GlobalRequestModel): Flow<PersonModel?> {
        return fetchData(isLoadingShown = true) {
            api.getPopularPeople(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getUpComingMovie(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getUpComingMovie(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }

    fun getMoviePopular(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getPopularMovie(authToken = requestModel.apiKey!!, page = requestModel.page!!, language = requestModel.language!!)
        }
    }
}