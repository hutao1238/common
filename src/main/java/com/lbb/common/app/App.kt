package com.lbb.common.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.lbb.common.di.component.AppComponent
import com.lbb.common.di.component.DaggerAppComponent
import com.lbb.common.di.module.AppModule
import com.lbb.common.di.module.HttpModule
import com.lbb.common.service.InitializeService


/**
 * Created by 胡涛.
 */
abstract class App : Application() {

    private var allActivities: HashSet<Activity>? = null

    override fun onCreate() {
        super.onCreate()
        instance = getAppInstance()
        baseUrl = getNetBaseUrl()
        //在子线程中完成其他初始化
        InitializeService.start(this)
    }

    abstract fun getNetBaseUrl(): String?

    abstract fun getAppInstance(): App?

    /**
     * 分包处理
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    /**
     * 添加activity
     */
    fun addActivity(act: Activity) {
        if (allActivities == null) {
            allActivities = HashSet()
        }
        allActivities?.add(act)
    }

    /**
     * 删除activity
     */
    fun removeActivity(act: Activity) {
        if (allActivities != null) {
            allActivities?.remove(act)
        }
    }

    /**
     * 退出app，循环杀死activity
     */
    fun exitApp() {
        if (allActivities != null) {
            allActivities?.let {
                synchronized(it) {
                    for (act in it) {
                        act.finish()
                    }
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
    }

    companion object {
        private val appComponents: AppComponent? = null
        var instance: App? = null
        private var baseUrl: String? = null

        fun getAppComponent(): AppComponent? {
            return appComponents ?: DaggerAppComponent.builder()
                            .appModule(AppModule(instance))
                            .httpModule(HttpModule(baseUrl))
                            .build()
        }
    }

}