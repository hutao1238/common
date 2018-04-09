package com.lbb.common.di.module

import com.lbb.common.app.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by 胡涛.
 */
@Module
class AppModule(private val application: App?) {
    @Provides
    @Singleton
    fun provideApplicationContext(): App  {
        if (application == null) {
            throw RuntimeException("请重写application继承App重写getAppInstance方法")
        }
        return application
    }
}