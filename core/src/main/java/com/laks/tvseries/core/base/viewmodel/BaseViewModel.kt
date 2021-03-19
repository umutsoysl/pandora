package com.laks.tvseries.core.base.viewmodel

import androidx.lifecycle.ViewModel
import com.laks.tvseries.core.base.service.BaseRepository
import com.laks.tvseries.core.base.service.BaseUseCase

abstract class BaseViewModel(var useCase: BaseUseCase?): ViewModel() {}