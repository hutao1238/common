package com.lbb.common.service

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.github.moduth.blockcanary.BlockCanary
import com.lbb.common.app.App
import com.lbb.common.utils.AppBlockCanaryContext
import com.squareup.leakcanary.LeakCanary

@SuppressLint("Registered")
/**
 * Created by 胡涛.
 */
class InitializeService : IntentService("InitializeService") {

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_INIT == action) {
                initApplication()
            }
        }
    }

    companion object {
        private val ACTION_INIT = "initApplication"
        fun start(context: Context) {
            val intent = Intent(context, InitializeService::class.java)
            intent.action = ACTION_INIT
            context.startService(intent)
        }
    }

    private fun initApplication() {

        //初始化内存泄漏检测
        LeakCanary.install(App.instance)

        //初始化过度绘制检测
        BlockCanary.install(applicationContext, AppBlockCanaryContext()).start()
    }
}