package com.laks.tvseries.core.base.service

import android.util.Log
import com.laks.tvseries.core.loading.MemoryCacheHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response

abstract class BaseRepository<TServiceInterface>(clazz: Class<TServiceInterface>) : KoinComponent {
    var isLoading: Boolean? = null
    var classTag: String? = null
    lateinit var requestUrl: String

    private val connectionManager: OkHttpClient by inject()
    val api = ServiceManager.createApi(clazz, connectionManager)

    inline fun <reified T : Any> fetchData(isLoadingShown: Boolean? = false,
                                           crossinline call: suspend (TServiceInterface) -> Response<T>): Flow<T?> {

        return flow {

            ServiceManager.classTag = classTag ?: ""
            ServiceManager.isLoadingShown = isLoadingShown!!

            try {
                requestUrl = api.toString()

                val apiResponse = call(api)
                requestUrl = apiResponse.raw().request().url().toString()

                if (apiResponse.isSuccessful) {
                    if (!ServiceManager.rawBodyValue!!.isNullOrEmpty()) {
                        emit(apiResponse.body())
                    } else {
                        // Error handler
                    }
                    hideLoadingFragment(requestUrl)
                } else {
                    // Error handler
                    hideLoadingFragment(requestUrl)
                }

            } catch (e: Exception) {
                // Error handler
                Log.d("Call-response:", e.toString())
            }
        }
    }

    fun hideLoadingFragment(url: String) {
        MemoryCacheHelper.disableLoading(classTag, url)
    }

}