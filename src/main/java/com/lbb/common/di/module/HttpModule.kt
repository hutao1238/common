package com.lbb.common.di.module

import com.lbb.common.contant.CONNECT_TIMEOUT
import com.lbb.common.contant.READ_TIMEOUT
import com.lbb.common.utils.LogInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by 胡涛.
 */
@Module
class HttpModule(val baseUrl: String?) {

    @Provides
    @Singleton
    fun provideHttpInstance(): Retrofit {
        if (baseUrl == null) {
            throw RuntimeException("请重写application继承App重写getNetBaseUrl方法")
        }
        val client = OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(LogInterceptor())
                .build()
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}