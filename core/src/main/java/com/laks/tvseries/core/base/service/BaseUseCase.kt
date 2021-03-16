package com.laks.tvseries.core.base.service

import com.laks.tvseries.core.base.model.BaseModel
import com.laks.tvseries.core.global.GlobalBody

@Suppress("UNCHECKED_CAST")
open class BaseUseCase(var repo: BaseRepository<*>) {

    var model: BaseModel? = null
    lateinit var request: Any

    fun <T> getRequest(): GlobalBody<T> {
        val request = GlobalBody<T>()
        request.content = model as T
        return request
    }

    fun <T : BaseModel> createRequest(model: T): GlobalBody<T> {
        val request = GlobalBody<T>()
        request.content = model
        return request
    }
}
