package com.laks.tvseries.pandora

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.dao.MediaDao
import com.laks.tvseries.core.data.db.DBMediaEntity
import com.laks.tvseries.core.data.main.MediaRepository
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.DiscoverRequestModel
import com.laks.tvseries.core.data.model.GenreListModel
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

    var genreTvList = MutableLiveData<GenreListModel>()

    val shimmerGenreTVVisible = MutableLiveData<Boolean>(true)

    var genreMovieList = MutableLiveData<GenreListModel>()

    val shimmerGenreMovieVisible = MutableLiveData<Boolean>(true)

    fun getDiscoverMovieList(page: Int, genre: String = "") {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val requestModel = DiscoverRequestModel()
                requestModel.page = page
                requestModel.genre = genre
                mediaRepo?.getDiscoverMoviesList(requestModel)?.collect { response ->
                    for (item in response?.results!!)  item.isMovie = true
                    discoverMovieList.postValue(response)
                    shimmerMovieVisible.postValue(false)
                }
            }
        }
    }

    fun getDiscoverTVList(page: Int, genre: String = "") {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val requestModel = DiscoverRequestModel()
                requestModel.page = page
                requestModel.genre = genre
                mediaRepo?.getDiscoverTvList(requestModel)?.collect { response ->
                    for (item in response?.results!!)  item.isMovie = false
                    discoverTVList.postValue(response)
                    shimmerTVVisible.postValue(false)
                }
            }
        }
    }

    fun getMovieGenreList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mediaRepo?.getMovieGenreList()?.collect { response ->
                    genreMovieList.postValue(response)
                    shimmerGenreMovieVisible.postValue(false)
                }
            }
        }
    }

    fun getTvGenreList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mediaRepo?.getTvGenreList()?.collect { response ->
                    genreTvList.postValue(response)
                    shimmerGenreTVVisible.postValue(false)
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