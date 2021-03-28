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

class MovieDetailViewModel(var scheduleRepo: ScheduleRepository?) : BaseViewModel(scheduleRepo) {

    var movieModel = MutableLiveData<MovieDetailModel>()

    fun getMovieDetail(movieID: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = MovieRequestModel()
                requestModel.movieID = movieID
                scheduleRepo?.getMovieDetail(requestModel)?.collect {
                    movieModel.postValue(it)
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
                }
            }
        }
    }
}