package com.lbb.common.di.component

import com.lbb.common.app.App
import com.lbb.common.di.module.AppModule
import com.lbb.common.di.module.HttpModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by 胡涛.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, HttpModule::class))
interface AppComponent {
    fun getContext(): App       //提供App的Context
    fun getHttp(): Retrofit   //提供http通讯能力
}