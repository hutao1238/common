package com.lbb.common.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import java.lang.ref.WeakReference

/**
 * Created by 胡涛.
 */
object StringUtil {
    fun setString(context: Context , string: String , color: Int , dp: Float , startIndex: Int , endIndex: Int): SpannableString {
        val mContext: WeakReference<Context> = WeakReference(context)
        val spannableString = SpannableString(string)
        val foregroundColorSpan = mContext.get()?.let { ForegroundColorSpan(ContextCompat.getColor(it , color)) }
        val relativeSizeSpan = RelativeSizeSpan(dp)
        spannableString.setSpan(foregroundColorSpan , startIndex , endIndex , Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(relativeSizeSpan , startIndex , endIndex , Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        return spannableString
    }

    fun subString(str: String?): List<String> {
        return when {
            str.isNullOrEmpty() -> listOf()
            str!!.contains(",") -> str.split(",")
            else -> listOf(str)
        }
    }

    /**
     * 替换身份证
     */
    fun replaceNum(num: String): String {
        if (num.isEmpty() && num.length < 8) return ""
        val pre = num.substring(0 , 4)
        val end = num.substring(num.length - 4 , num.length)
        return "$pre***********$end"
    }
}