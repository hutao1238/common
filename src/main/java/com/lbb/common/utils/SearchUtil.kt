package com.lbb.common.utils

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.trello.rxlifecycle2.android.ActivityEvent
import java.lang.ref.WeakReference

/**
 * Created by 胡涛.
 */
object SearchUtil {
    fun search(editText: EditText, context: Context, callback: () -> Unit) {
        val mContext: WeakReference<Context> = WeakReference(context)
        editText.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                // 先隐藏键盘
                (mContext.get()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow((mContext.get() as AppCompatActivity).currentFocus
                                .windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                val searchContext = editText.text.toString().trim()
                if (searchContext.isEmpty()) {
                    Toast.makeText(mContext.get(), "输入框为空，请输入", Toast.LENGTH_SHORT).show()
                } else {
                    // 调用搜索的API方法
                    callback.invoke()
                }
            }
            false
        }
    }


}