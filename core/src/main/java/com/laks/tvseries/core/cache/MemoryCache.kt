package com.laks.tvseries.core.cache

import com.laks.tvseries.core.base.model.BaseModel
import com.laks.tvseries.core.base.service.BaseResponse

class MemoryCache {

    companion object {
        val cache = MemoryCache()
    }

    var map: MutableMap<String, Any> = mutableMapOf()

    fun <U : Any> findMemoryCacheValue(cacheID: String): U? {
        return map[cacheID] as U?
    }

    fun findMemoryCacheValueAny(cacheID: String): Any? {
        return  cache.map[cacheID]
    }

    fun findMemoryCacheValue(cacheID: String): BaseModel? {
        return  cache.map[cacheID] as? BaseModel
    }

    fun setMemoryCacheValue(cacheID: String, value: BaseModel) {
        cache.map[cacheID] = value
    }

    fun setMemoryCacheValue(cacheID: String, value: BaseResponse) {
        cache.map[cacheID] = value
    }

    fun setMemoryCacheValue(cacheID: String, value: Any) {
        cache.map[cacheID] = value
    }
}