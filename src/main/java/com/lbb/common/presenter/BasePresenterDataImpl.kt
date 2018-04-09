package com.lbb.common.presenter

import android.app.Activity
import com.lbb.common.contract.BasePresenter
import com.lbb.common.contract.BaseView
import com.lbb.common.model.bean.BaseBean
import com.trello.rxlifecycle2.LifecycleTransformer
import javax.inject.Inject

/**
 * Created by 胡涛.
 */
abstract class BasePresenterDataImpl:BasePresenter<Any> {
    @Inject
    lateinit var mBaseView: BaseView

    override fun getDataBean(arguments: Array<String>?, transformer: LifecycleTransformer<BaseBean<Any>>) {

    }

    override fun getDataList(arguments: Array<String>?, transformer: LifecycleTransformer<BaseBean<List<Any>>>) {

    }

    override fun getMoreDataList(arguments: Array<String>?, transformer: LifecycleTransformer<BaseBean<List<Any>>>) {

    }

}