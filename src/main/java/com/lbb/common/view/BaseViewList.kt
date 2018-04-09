package com.lbb.common.view

import com.lbb.common.contract.BaseView

/**
 * Created by 胡涛.
 */
interface BaseViewList<in T> : BaseView {
    override fun onSuccessBean(mode: Any) {
        
    }

    override fun onSuccess(list: List<Any>) {
        val listT = list as List<T>
        onSuccessList(listT)
    }

    fun onSuccessList(listT: List<T>)
}