package com.laks.tvseries.featuresearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.MovieRequestModel
import com.laks.tvseries.core.data.model.PersonModel
import com.laks.tvseries.core.data.search.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(var searchRepository: SearchRepository?) : BaseViewModel(searchRepository) {

    var responseMovieList = MutableLiveData<DiscoverMovieListModel>()
    var responseTvList = MutableLiveData<DiscoverMovieListModel>()
    var responseActorList = MutableLiveData<PersonModel>()
    var progressBarVisible = MutableLiveData<Boolean>()

    var searchQuery = MutableLiveData<String>()

    fun searchMovie(page: Int? = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = MovieRequestModel()
                requestModel.page = page
                requestModel.query = searchQuery.value
                searchRepository?.searchMovie(requestModel)?.collect {
                    responseMovieList.postValue(it)
                    searchTvSeries(page)
                    progressBarVisible.postValue(false)
                }
            }
        }
    }

    fun searchTvSeries(page: Int? = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = MovieRequestModel()
                requestModel.page = page
                requestModel.query = searchQuery.value
                searchRepository?.searchTv(requestModel)?.collect {
                    responseTvList.postValue(it)
                    searchActor(page)
                    progressBarVisible.postValue(false)
                }
            }
        }
    }

    fun searchActor(page: Int? = 1) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = MovieRequestModel()
                requestModel.page = page
                requestModel.query = searchQuery.value
                searchRepository?.searchActor(requestModel)?.collect {
                    responseActorList.postValue(it)
                    progressBarVisible.postValue(false)
                }
            }
        }
    }


}
