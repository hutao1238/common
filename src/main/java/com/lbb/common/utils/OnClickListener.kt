package com.lbb.common.utils

import android.view.View
import com.lbb.common.contant.MIN_CLICK_DELAY_TIME
import com.lbb.common.contant.id
import com.lbb.common.contant.lastClickTime
import java.util.*

/**
* Created by 胡涛.
*/
interface OnNoDoubleClickListener : View.OnClickListener {

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

    fun onNoDoubleClick(v: View)
}