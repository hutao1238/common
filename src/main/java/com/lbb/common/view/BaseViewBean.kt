package com.lbb.common.view

import com.lbb.common.contract.BaseView

/**
 * Created by 胡涛.
 */
interface BaseViewBean<in T> : BaseView {
    override fun onSuccessBean(mode: Any) {
        val t = mode as T
        onSuccessT(t)
    }

    override fun onSuccess(list: List<Any>) {

    }

    fun onSuccessT(t: T)
}