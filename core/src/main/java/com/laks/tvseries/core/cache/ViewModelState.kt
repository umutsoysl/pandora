package com.laks.tvseries.core.cache

import androidx.lifecycle.MutableLiveData
import com.laks.tvseries.core.base.model.BaseExceptionModel

open class ViewModelState {
    var loading: MutableLiveData<MutableMap<String, Boolean>> = MutableLiveData<MutableMap<String, Boolean>>().apply { this.value = mutableMapOf() }
    var error: MutableLiveData<BaseExceptionModel> = MutableLiveData()
}