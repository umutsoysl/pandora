package com.laks.tvseries.core.data.search

import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.MovieRequestModel
import com.laks.tvseries.core.data.model.PersonModel
import kotlinx.coroutines.flow.Flow

class SearchRepository: BaseRepository<SearchApi>(SearchApi::class.java) {

    fun searchMovie(requestModel: MovieRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.searchMovie(query = requestModel.query!!, page = requestModel.page!!, authToken = requestModel.apiKey!!, language = requestModel.language!!)
        }
    }

    fun searchTv(requestModel: MovieRequestModel): Flow<DiscoverMovieListModel?> {
        return fetchData(isLoadingShown = true) {
            api.searchTv(query = requestModel.query!!, page = requestModel.page!!, authToken = requestModel.apiKey!!, language = requestModel.language!!)
        }
    }

    fun searchActor(requestModel: MovieRequestModel): Flow<PersonModel?> {
        return fetchData(isLoadingShown = true) {
            api.searchActor(query = requestModel.query!!, page = requestModel.page!!, authToken = requestModel.apiKey!!, language = requestModel.language!!)
        }
    }
}