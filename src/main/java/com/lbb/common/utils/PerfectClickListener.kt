package com.lbb.common.utils

import android.view.View
import java.util.*

/**
* Created by 胡涛.
*/
abstract class PerfectClickListener : View.OnClickListener {
    val MIN_CLICK_DELAY_TIME = 1000
    private var lastClickTime: Long = 0
    private var id = -1

    override fun onClick(v: View) {
        val currentTime = Calendar.getInstance().timeInMillis
        val mId = v.id
        if (id != mId) {
            id = mId
            lastClickTime = currentTime
            onNoDoubleClick(v)
            return
        }
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            onNoDoubleClick(v)
        }
    }

    abstract fun onNoDoubleClick(v: View)
}