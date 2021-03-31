package com.laks.tvseries.featurecategory.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.data.main.ScheduleRepository
import com.laks.tvseries.core.data.model.MovieDetailModel
import com.laks.tvseries.core.data.model.MovieRequestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailViewModel(var scheduleRepo: ScheduleRepository?) : BaseViewModel(scheduleRepo) {

    var movieModel = MutableLiveData<MovieDetailModel>()
    var releaseDate = MutableLiveData<String>()
    var loadingComplete = MutableLiveData<Boolean>(false)
    private val _moreClickEvent = MutableLiveData<Unit>()
    val moreButtonClickEvent: LiveData<Unit> = _moreClickEvent
    var videoKey = MutableLiveData<String>()

    fun moreButtonOnClickListener() {
        _moreClickEvent.value = Unit
    }

    fun getMovieDetail(movieID: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = MovieRequestModel()
                requestModel.movieID = movieID
                scheduleRepo?.getMovieDetail(requestModel)?.collect {
                    loadingComplete.postValue(true)
                    movieModel.postValue(it)
                    releaseDate.postValue(it?.releaseDate?.let { it1 -> getFormatDate(it1) })
                    getMovieVideo(requestModel)
                }
            }
        }
    }

    fun getTVDetail(movieID: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = MovieRequestModel()
                requestModel.movieID = movieID
                scheduleRepo?.getTVDetail(requestModel)?.collect {
                    loadingComplete.postValue(true)
                    movieModel.postValue(it)
                    releaseDate.postValue(it?.releaseDate?.let { it1 -> getFormatDate(it1) })
                    getTVVideo(requestModel)
                }
            }
        }
    }

    private fun getMovieVideo(requestModel: MovieRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleRepo?.getMovieVideo(requestModel)?.collect {
                    videoKey.postValue(it?.let {it.results.first().key})
                }
            }
        }
    }

    private fun getTVVideo(requestModel: MovieRequestModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleRepo?.getTvVideo(requestModel)?.collect {
                    videoKey.postValue(it?.let {it.results.first().key})
                }
            }
        }
    }

    private fun getFormatDate(releaseDate: String): String {
        var format = SimpleDateFormat("yyyy-mm-dd")
        val newDate: Date = format.parse(releaseDate)

        format = SimpleDateFormat("MMM yyyy")
        return  format.format(newDate)
    }
}