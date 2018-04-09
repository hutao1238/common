package com.lbb.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.lbb.common.app.App
import javax.inject.Inject

/**
 * Created by 胡涛.
 */
object MsgUtil {

    private var mToast: Toast? = null

    @SuppressLint("ShowToast")
    fun showMsg(context: Context,msg: String) {
        if (mToast == null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
        }
        mToast?.setText(msg)
        mToast?.show()
    }
}