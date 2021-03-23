package com.laks.tvseries.pandora

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.data.ScheduleRepository
import com.laks.tvseries.core.data.model.ScheduleAllListModelResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(var scheduleRepo: ScheduleRepository?) : BaseViewModel(scheduleRepo) {

    val allScheduleList = MutableLiveData<ArrayList<ScheduleAllListModelResponse>>()

    val scheduleList = MutableLiveData<ArrayList<ScheduleAllListModelResponse>>()

    fun getScheduleFullList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleRepo?.getScheduleFull()?.collect { response ->
                    allScheduleList.postValue(response)
                }
            }
        }
    }

    fun getSchedule() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                scheduleRepo?.getSchedule()?.collect { response ->
                    scheduleList.postValue(response)
                }
            }
        }
    }
}