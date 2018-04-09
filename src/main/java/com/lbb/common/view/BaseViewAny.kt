package com.lbb.common.view

import com.lbb.common.contract.BaseView

/**
 * Created by 胡涛.
 */
interface BaseViewAny:BaseView {
    override fun onSuccess(list: List<Any>) {

    }

    override fun onSuccessBean(mode: Any) {

    }
}