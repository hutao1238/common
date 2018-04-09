package com.lbb.common.utils

import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer

/**
 * Created by 胡涛 on 2017/9/1.
 * 作用：
 */
object BannerUtil {
    fun initBanner(banner: Banner?, images: List<String>?) {
        val list = arrayListOf<String>()
        if (images == null || images.isEmpty()) {
            list.add("")
        } else {
            images.forEach {
                list.add(it)
            }
        }
        //设置banner样式
        banner?.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        //设置图片加载器
        banner?.setImageLoader(GlideImageLoader())
        //设置图片集合
        banner?.setImages(list)
        //设置banner动画效果
        banner?.setBannerAnimation(Transformer.DepthPage)
        //设置自动轮播，默认为true
        banner?.isAutoPlay(true)
        //设置轮播时间
        banner?.setDelayTime(2500)
        //设置指示器位置（当banner模式中有指示器时）
        banner?.setIndicatorGravity(BannerConfig.CENTER)
        //banner设置方法全部调用完毕时最后调用
        banner?.start()
    }

}