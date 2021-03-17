package com.laks.tvseries.core.cache

import com.laks.tvseries.core.base.model.BaseModel
import com.laks.tvseries.core.base.service.BaseResponse

class MemoryCache {

    companion object {
        val cache = MemoryCache()
    }

    var map: MutableMap<String, Any> = mutableMapOf()
    var transfer: MutableMap<String, Any> = mutableMapOf()

    fun <U : Any> findMemoryCacheValue(cacheID: String): U? {
        return map[cacheID] as U?
    }

    fun findMemoryCacheValue(cacheID: String): BaseModel? {
        return map[cacheID] as? BaseModel
    }

    fun findTransferValue(transferId: String): BaseModel? {
        return transfer[transferId] as? BaseModel
    }

    fun setMemoryCachevValue(cacheID: String, value: BaseModel) {
        cache.map[cacheID] = value
    }

    fun setMemoryCacheValue(cacheID: String, value: BaseResponse) {
        cache.map[cacheID] = value
    }

    fun setMemoryCacheValue(cacheID: String, value: Any) {
        cache.map[cacheID] = value
    }

    fun setTransferValue(transferId: String, value: BaseModel) {
        cache.transfer[transferId] = value
    }

    fun setTransferValue(transferId: String, value: Any) {
        cache.transfer[transferId] = value
    }

    fun setTransferValue(transferId: String, value: BaseResponse) {
        cache.transfer[transferId] = value
    }

    fun removeMemoryCacheValue(cacheID: String) {
        cache.map.remove(cacheID)
    }

    fun removeTransferValue(transferId: String) {
        cache.transfer.remove(transferId)
    }

    fun removeMemoryCacheValue() {
        cache.map.clear()
    }

    fun removeTransferValue() {
        cache.transfer.clear()
    }

}