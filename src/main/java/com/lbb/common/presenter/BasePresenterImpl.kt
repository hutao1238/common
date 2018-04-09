package com.lbb.common.presenter

import com.lbb.common.app.App
import com.lbb.common.contract.BasePresenter
import com.lbb.common.contract.BaseView
import com.lbb.common.model.bean.BaseBean
import com.lbb.common.model.event.BaseModelImplCommon
import com.trello.rxlifecycle2.LifecycleTransformer
import javax.inject.Inject

/**
 * Created by 胡涛.
 */
open class BasePresenterImpl<T, M : BaseModelImplCommon<T>> : BasePresenter<T> {

    @Inject
    lateinit var application: App

    @Inject
    lateinit var model: M

    @Inject
    lateinit var mBaseView: BaseView

    override fun getDataBean(arguments: Array<String>?, transformer: LifecycleTransformer<BaseBean<T>>) {
        argumentsAfter = getDataBefore(arguments, transformer)
        if (arguments != null && argumentsAfter == null) {
            return
        }
        model.getDataBeanI(argumentsAfter, page.toString(), num.toString(), transformer, { mode: T ->
            getDataAfterT(arguments,mode)
            mBaseView.onSuccessBean(mode!!)
        }, { s: String ->
            mBaseView.onError(s)
        })
    }

    private var page: Int = 1
    private var num: Int = 10
    private var list = arrayListOf<T>()
    private var argumentsAfter: Array<String>? = null
    override fun getDataList(arguments: Array<String>?, transformer: LifecycleTransformer<BaseBean<List<T>>>) {
        page = 1
        argumentsAfter = getDataBefore(arguments, transformer)
        if (arguments != null && argumentsAfter == null) {
            return
        }
        model.getDataList(argumentsAfter, page.toString(), num.toString(), transformer, { list: List<T> ->
            this.list.clear()
            this.list.addAll(list)
            getDataAfterList(arguments,this.list)
            mBaseView.onSuccess(this.list as List<Any>)
        }, { s: String ->
            mBaseView.onError(s)
        })
    }

    open fun getDataAfterT(arguments: Array<String>?,mode: T){

    }

    open fun getDataAfterList(arguments: Array<String>?,list: List<T>){

    }

    open fun getDataBefore(arguments: Array<String>?, transformer: LifecycleTransformer<*>): Array<String>? =
            null

    override fun getMoreDataList(arguments: Array<String>?, transformer: LifecycleTransformer<BaseBean<List<T>>>) {
        page += 1
        model.getDataList(arguments, page.toString(), num.toString(), transformer, { list: List<T> ->
            if (list.isEmpty()) page -= 1
            this.list.addAll(list)
            getDataAfterList(arguments,this.list)
            mBaseView.onSuccess(this.list as List<Any>)
        }, { s: String ->
            page -= 1
            mBaseView.onError(s)
        })
    }
}