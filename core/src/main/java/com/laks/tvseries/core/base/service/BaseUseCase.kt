package com.laks.tvseries.core.base.service

import com.laks.tvseries.core.base.model.BaseModel
import com.laks.tvseries.core.global.GlobalRequest

@Suppress("UNCHECKED_CAST")
open class BaseUseCase(var repository: BaseRepository<*>) {
    var model: BaseModel? = null
    lateinit var request: Any

    fun <T> getRequest(): GlobalRequest<T> {
        val request = GlobalRequest<T>()
        request.content = model as T
        return request
    }
}