package com.lbb.common.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lbb.common.R
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.lang.ref.WeakReference

/**
 * Created by 胡涛 on 2017/9/13.
 * 作用：
 */
object GlideUtil {
    private val mOption = RequestOptions()
            .placeholder(R.mipmap.default_img)
            .centerCrop()
            .error(R.mipmap.default_img)
            .dontAnimate()

    fun showImgNormal(context: Context, path: String?, imageView: ImageView?) {
        val mContext: WeakReference<Context> = WeakReference(context)
        Glide.with(mContext.get())
                .load(path)
                .apply(mOption)
                .into(imageView)
    }

    fun showRoundedImg(context: Context, path: String?, imageView: ImageView?) {
        val mContext: WeakReference<Context> = WeakReference(context)
        val rOption = RequestOptions().transform(RoundedCornersTransformation(10, 0))
        Glide.with(mContext.get())
                .load(path)
                .apply(mOption)
                .apply(rOption)
                .into(imageView)
    }

    fun showCircleImg(context: Context, path: String?, imageView: ImageView?) {
        val mContext: WeakReference<Context> = WeakReference(context)
        val cOption = RequestOptions()
                .placeholder(R.mipmap.portrait)
                .error(R.mipmap.portrait)
                .circleCrop()
        Glide.with(mContext.get())
                .load(path)
                .apply(mOption)
                .apply(cOption)
                .into(imageView)
    }
}