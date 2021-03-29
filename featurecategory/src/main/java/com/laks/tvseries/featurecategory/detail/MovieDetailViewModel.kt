package com.laks.tvseries.featurecategory.detail

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

    fun getMovieDetail(movieID: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = MovieRequestModel()
                requestModel.movieID = movieID
                scheduleRepo?.getMovieDetail(requestModel)?.collect {
                    movieModel.postValue(it)
                    releaseDate.postValue(it?.releaseDate?.let { it1 -> getFormatDate(it1) })
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
                    movieModel.postValue(it)
                    releaseDate.postValue(it?.releaseDate?.let { it1 -> getFormatDate(it1) })
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