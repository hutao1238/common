package com.lbb.common.contract

import com.lbb.common.model.bean.BaseBean
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * Created by 胡涛 on 2017/9/22.
 * 作用：
 */
interface BaseView {
    fun onSuccessBean(mode: Any)
    fun onSuccess(list: List<Any>)
    fun onError(message: String)
}

interface BasePresenter<T> {
    fun getDataBean(arguments: Array<String>?, transformer: LifecycleTransformer<BaseBean<T>>)
    fun getDataList(arguments: Array<String>?, transformer: LifecycleTransformer<BaseBean<List<T>>>)
    fun getMoreDataList(arguments: Array<String>?, transformer: LifecycleTransformer<BaseBean<List<T>>>)
}

interface BaseModel<T> {
    fun getDataList(arguments: Array<String>?, page: String, num: String, transformer: LifecycleTransformer<BaseBean<List<T>>>, onSuccess: (List<T>) -> Unit, onError: (String) -> Unit)
    fun getDataBeanI(arguments: Array<String>?, page: String, num: String, transformer: LifecycleTransformer<BaseBean<T>>, onSuccess: (T) -> Unit, onError: (String) -> Unit)
}