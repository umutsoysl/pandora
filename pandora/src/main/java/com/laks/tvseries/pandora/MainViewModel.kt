package com.laks.tvseries.pandora

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.data.main.ScheduleRepository
import com.laks.tvseries.core.data.model.DiscoverMovieListModel
import com.laks.tvseries.core.data.model.MovieRequestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(var scheduleRepo: ScheduleRepository?) : BaseViewModel(scheduleRepo) {

    val discoverMovieList = MutableLiveData<DiscoverMovieListModel>()

    val shimmerVisible = MutableLiveData<Boolean>(true)

    fun getDiscoverMovieList(page: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var requestModel = MovieRequestModel()
                requestModel.page = page
                scheduleRepo?.getDiscoverMoviesList(requestModel)?.collect { response ->
                    discoverMovieList.postValue(response)
                    shimmerVisible.postValue(false)
                }
            }
        }
    }

}