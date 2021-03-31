package com.laks.tvseries.core.base.service

import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.core.loading.MemoryCacheHelper
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceManager {
    companion object {
        private const val CONNECTION_TIME_OUT: Long = 60000
        var classTag: String = ""
        var isLoadingShown: Boolean = false
        var rawBodyValue: String? = ""

        fun interceptOkHttpClient(): OkHttpClient {
            val okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.addInterceptor { chain ->
                val original = chain.request()
                enableLoading(original.url().toString())
                chain.proceed(original).also {
                    val responseBodyCopy: ResponseBody = it.peekBody(Long.MAX_VALUE)
                    rawBodyValue = responseBodyCopy.string()
                }
            }
            okHttpClientBuilder.connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
            okHttpClientBuilder.readTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
            return okHttpClientBuilder.build()
        }


        fun <T> createApi(clazz: Class<T>, okHttpClient: OkHttpClient): T {

            return Retrofit.Builder()
                    .baseUrl(GlobalConstants.SERVER_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(clazz)
        }

        private fun enableLoading(url: String) {
            if (isLoadingShown) {
                return
            }
            MemoryCacheHelper.enableLoading(classTag, url)
        }
    }
}
