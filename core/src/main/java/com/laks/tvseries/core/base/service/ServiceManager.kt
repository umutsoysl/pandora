package com.laks.tvseries.core.base.service

import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.core.loading.MemoryCacheHelper
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
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
                val original = requestBuilder(chain.request())
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

        private fun requestBuilder(request: Request): Request {
            val originalHttpUrl: HttpUrl = request.url()

            val language =  MemoryCache.cache.findMemoryCacheValueAny(GlobalConstants.SHARED_LANGUAGE_WITH_CODE_COUNTRY) as String

            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", "api_key")
                    .addQueryParameter("language", language)
                    .build()

            val requestBuilder: Request.Builder = request.newBuilder()
                    .url(url)

            return requestBuilder.build()
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
