package com.laks.tvseries.pandora

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.data.ScheduleRepository
import com.laks.tvseries.core.data.model.ScheduleModelResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(var scheduleRepo: ScheduleRepository?) : BaseViewModel(scheduleRepo) {

    val allScheduleList = MutableLiveData<ScheduleModelResponse>()

    fun getScheduleFullList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = scheduleRepo?.getScheduleFull() as ScheduleModelResponse
                allScheduleList.postValue(response)
            }
        }
    }
}