package com.lbb.common.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import java.lang.ref.WeakReference

/**
 * Created by 胡涛.
 */
object DisplayUtil {
    /**
     * 屏幕高度

     * @param context
     * *
     * @return
     */
    fun getMobileHeight(context: Context): Int {
        val mContext: WeakReference<Context> = WeakReference(context)
        val dm = DisplayMetrics()
        (mContext.get() as Activity).windowManager.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }

    /**
     * 屏幕宽度

     * @param context
     * *
     * @return
     */
    fun getMobileWidth(context: Context): Int {
        val mContext: WeakReference<Context> = WeakReference(context)
        val dm = DisplayMetrics()
        (mContext.get() as Activity).windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels

    }

    /**
     * 得到状态栏的高度
     * @param context
     * *
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        val mContext: WeakReference<Context> = WeakReference(context)
        var result = 0
        val resourceId = mContext.get()?.resources?.getIdentifier("status_bar_height", "dimen",
                "android")
        if (resourceId != null && resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * 根据手机的分辨率dp 转成px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val mContext: WeakReference<Context> = WeakReference(context)
        val scale = mContext.get()?.resources?.displayMetrics?.density
        return scale?.let { (dpValue * it + 0.5f).toInt() } ?: 0
    }

    /**
     * 根据手机的分辨率px(像素) 转成dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val mContext: WeakReference<Context> = WeakReference(context)
        val scale = mContext.get()?.resources?.displayMetrics?.density
        return scale?.let { (pxValue / it + 0.5f).toInt() } ?: 0
    }


    fun sp2px(context: Context, sp: Float): Float {
        val mContext: WeakReference<Context> = WeakReference(context)
        val scale = mContext.get()?.resources?.displayMetrics?.scaledDensity
        return scale?.let { sp * it } ?: 0F
    }

    private fun getBitmapWidth(context: Context, dp: Float, multiple: Int): Int {
        return getMobileWidth(context) / multiple - dip2px(context, dp)
    }

    private fun getBitmapHeight(context: Context, dp: Float, multiple: Int, width: Int, height: Int): Int {
        return (getBitmapWidth(context, dp, multiple) * height.toFloat() / width.toFloat()).toInt()
    }

    fun setBitmapHeight(context: Context, view: View, singleDp: Float, multiple: Int, width: Int, height: Int) {
        val layoutParams = view.layoutParams
        layoutParams.height = getBitmapHeight(context, singleDp, multiple, width, height)
        view.layoutParams = layoutParams
    }

    fun getVersionName(context: Context): String? {
        val mContext: WeakReference<Context> = WeakReference(context)
        val packageManager = mContext.get()?.packageManager
        val packInfo = packageManager?.getPackageInfo(mContext.get()?.packageName, 0)
        return packInfo?.versionName
    }

}