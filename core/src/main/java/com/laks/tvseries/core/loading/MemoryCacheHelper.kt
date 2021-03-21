package com.laks.tvseries.core.loading

import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.cache.ViewModelState

object MemoryCacheHelper {

    fun aliveLoading(classTag: String?): Boolean {
        val state = findLoadingCache(classTag) ?: return false
        val values = state.loading.value ?: return false

        for (item in values) {
            if (item.value) { return true }
        }
        return false
    }

    fun findLoadingCache(classTag: String?): ViewModelState? {
        classTag?.let {
            if (MemoryCache.cache.map.containsKey(classTag)) return MemoryCache.cache.map[classTag] as ViewModelState
        }
        return null
    }

    fun enableLoading(classTag: String?, url: String?) {
        classTag ?: return
        url ?: return
        val loadingState = findLoadingCache(classTag) ?: return
        loadingState.loading.value ?: return

        loadingState.loading.value!![url] = true
        loadingState.loading.postValue(loadingState.loading.value)
    }

    fun disableLoading(classTag: String?, url: String) {
        classTag ?: return
        val loadingState = findLoadingCache(classTag) ?: return
        loadingState.loading.value ?: return

        loadingState.loading.value!![url] = false
        loadingState.loading.postValue(loadingState.loading.value)
    }

    fun setLoadingCache(classTag: String?, value: ViewModelState) {
        classTag?.let {
            MemoryCache.cache.map.put(classTag, value)
        }
    }

    fun removeCache(classTag: String?) {
        classTag?.let {
            MemoryCache.cache.map.remove(classTag)
        }
    }
}