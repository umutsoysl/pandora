package com.laks.tvseries.pandora

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.data.main.MediaRepository
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.GlobalRequestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(var mediaRepo: MediaRepository?) : BaseViewModel(mediaRepo) {

    val discoverMovieList = MutableLiveData<DiscoverMovieListModel>()

    val discoverTVList = MutableLiveData<DiscoverMovieListModel>()

    val shimmerMovieVisible = MutableLiveData<Boolean>(true)

    val shimmerTVVisible = MutableLiveData<Boolean>(true)

    fun getDiscoverMovieList(page: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = GlobalRequestModel()
                requestModel.page = page
                mediaRepo?.getDiscoverMoviesList(requestModel)?.collect { response ->
                    for (item in response?.results!!)  item.isMovie = true
                    discoverMovieList.postValue(response)
                    shimmerMovieVisible.postValue(false)
                }
            }
        }
    }

    fun getDiscoverTVList(page: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = GlobalRequestModel()
                requestModel.page = page
                mediaRepo?.getDiscoverTvList(requestModel)?.collect { response ->
                    for (item in response?.results!!)  item.isMovie = false
                    discoverTVList.postValue(response)
                    shimmerTVVisible.postValue(false)
                }
            }
        }
    }

}