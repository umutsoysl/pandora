package com.laks.tvseries.core.data.category

import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.GlobalRequestModel
import com.laks.tvseries.core.data.model.PersonModel
import kotlinx.coroutines.flow.Flow

class CategoryRepository: BaseRepository<CategoryApi>(CategoryApi::class.java) {

    fun getTrending(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getTrending(type = requestModel.type!!, time = requestModel.time!!)
        }
    }

    fun getNowPlaying(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getNowPlaying( page = requestModel.page!!)
        }
    }

    fun getTvPopularShows(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getPopularTVShows(page = requestModel.page!!)
        }
    }

    fun getPopularPeople(requestModel: GlobalRequestModel): Flow<PersonModel?> {
        return fetchData(isLoadingShown = true) {
            api.getPopularPeople(page = requestModel.page!!)
        }
    }

    fun getUpComingMovie(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getUpComingMovie(page = requestModel.page!!)
        }
    }

    fun getMoviePopular(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getPopularMovie(page = requestModel.page!!)
        }
    }

    fun getMovieTopRated(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getMovieTopRated(page = requestModel.page!!)
        }
    }

    fun getNetworkTvShows(requestModel: GlobalRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.getPublisherTvShowList(page = requestModel.page!!, publisherID = requestModel.movieID!!.toInt())
        }
    }
}