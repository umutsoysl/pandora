package com.laks.tvseries.pandora

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.dao.MediaDao
import com.laks.tvseries.core.data.db.DBMediaEntity
import com.laks.tvseries.core.data.main.MediaRepository
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.GlobalRequestModel
import com.laks.tvseries.core.db.PandoraDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(var mediaRepo: MediaRepository?) : BaseViewModel(mediaRepo) {

    val discoverMovieList = MutableLiveData<DiscoverMovieListModel>()

    val discoverTVList = MutableLiveData<DiscoverMovieListModel>()

    val shimmerMovieVisible = MutableLiveData<Boolean>(true)

    val shimmerTVVisible = MutableLiveData<Boolean>(true)

    var mediaDBMediaEntity = MutableLiveData<DBMediaEntity>()

    var movieWatchList = MutableLiveData<List<DBMediaEntity>>()

    val shimmerVisible = MutableLiveData<Boolean>(true)

    var tvWatchList = MutableLiveData<List<DBMediaEntity>>()

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

    fun findDBEntity(context: Context, id: Long) {
        val mediaDao: MediaDao? = PandoraDatabase.getDatabase(context)?.mediaDao()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                PandoraDatabase.execute.execute {
                    mediaDao?.findMedia(mediaID = id)?.let {
                        if (it.id > 0) {
                            mediaDBMediaEntity.postValue(it)
                        }
                    }
                }
            }
        }
    }

    fun getDBWatchList(context: Context, isMovie: Boolean) {
        val mediaDao: MediaDao? = PandoraDatabase.getDatabase(context)?.mediaDao()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                PandoraDatabase.execute.execute {
                    mediaDao?.getWatchList(isWatched = true, isMovie = isMovie)?.let {
                        if(isMovie) {
                            movieWatchList.postValue(it)
                        } else {
                            tvWatchList.postValue(it)
                        }
                    }
                }
            }
        }
    }

}