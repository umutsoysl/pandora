package com.laks.tvseries.pandora

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.laks.tvseries.core.base.viewmodel.BaseViewModel
import com.laks.tvseries.core.data.ScheduleRepository
import com.laks.tvseries.core.data.ScheduleUseCase
import com.laks.tvseries.core.data.model.ScheduleModelResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(var scheduleUseCase: ScheduleUseCase?) : BaseViewModel(scheduleUseCase) {

    fun getScheduleFullList(): LiveData<ScheduleModelResponse> {
        val result = MutableLiveData<ScheduleModelResponse>()
        viewModelScope.launch {
            scheduleUseCase?.getScheduleFullList()?.collect { response ->
                response?.let {
                    result.postValue(response)
                }
            }
        }
        return result
    }
}