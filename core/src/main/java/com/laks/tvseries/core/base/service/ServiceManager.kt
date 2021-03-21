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

        fun <T> createApi(clazz: Class<T>, okHttpClient: OkHttpClient): T {

            return Retrofit.Builder()
                    .baseUrl(GlobalConstants.SERVER_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(clazz)
        }
    }
}
