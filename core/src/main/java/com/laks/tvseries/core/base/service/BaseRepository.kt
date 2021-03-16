package com.laks.tvseries.core.base.service

import com.laks.tvseries.core.global.GlobalResponse
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
                                           isNetworkErrorShown: Boolean = false,
                                               crossinline call: suspend (TServiceInterface) -> Response<GlobalResponse<T>>): Flow<T?> {

        return flow {

            try {
                ServiceManager.classTag = classTag ?: ""
                ServiceManager.isLoadingShown = isLoadingShown!!

                requestUrl = api.toString()

                val apiResponse = call(api)
                requestUrl = apiResponse.raw().request().url().toString()

                if (apiResponse.isSuccessful) {
                    emit(apiResponse.body()?.content)
                } else {
                    // Error handler
                }

            } catch (e: Exception) {
                // Error handler
            }

        }
    }

}