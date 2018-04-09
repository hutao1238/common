package com.lbb.common.model.event

import com.lbb.common.app.App
import com.lbb.common.contract.BaseModel
import com.lbb.common.model.bean.BaseBean
import com.trello.rxlifecycle2.LifecycleTransformer
import org.jetbrains.anko.AnkoLogger
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by 胡涛.
 */
open class BaseModelImplCommon<T>:BaseModel<T>, AnkoLogger {

    @Inject
    lateinit var application: App

    @Inject
    lateinit var retrofit: Retrofit

    protected val map = hashMapOf<String, String>()

    override fun getDataBeanI(arguments: Array<String>?, page: String, num: String, transformer: LifecycleTransformer<BaseBean<T>>, onSuccess: (T) -> Unit, onError: (String) -> Unit) {
        getDataBean(arguments,transformer, onSuccess, onError)
    }

    override fun getDataList(arguments: Array<String>?, page: String, num: String, transformer: LifecycleTransformer<BaseBean<List<T>>>, onSuccess: (List<T>) -> Unit, onError: (String) -> Unit) {
        getDataPage(arguments, page, num, transformer, onSuccess, onError)
        getDataNoPage(arguments, transformer, onSuccess, onError)
    }

    open fun getDataBean(arguments: Array<String>?,transformer: LifecycleTransformer<BaseBean<T>>, onSuccess: (T) -> Unit, onError: (String) -> Unit) =
            Unit


    open fun getDataNoPage(arguments: Array<String>?, transformer: LifecycleTransformer<BaseBean<List<T>>>, onSuccess: (List<T>) -> Unit, onError: (String) -> Unit) =
            Unit

    open fun getDataPage(arguments: Array<String>?, page: String, num: String, transformer: LifecycleTransformer<BaseBean<List<T>>>, onSuccess: (List<T>) -> Unit, onError: (String) -> Unit) =
            Unit
}