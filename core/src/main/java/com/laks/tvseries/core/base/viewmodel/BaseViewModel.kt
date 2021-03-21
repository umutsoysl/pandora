package com.laks.tvseries.core.base.viewmodel

import androidx.lifecycle.ViewModel
import com.laks.tvseries.core.base.service.BaseRepository

abstract class BaseViewModel(var repository: BaseRepository<*>?): ViewModel() {}