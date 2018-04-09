package com.lbb.common.utils

import android.util.Log
import com.lbb.common.model.bean.BaseBean
import com.lbb.common.model.bean.BaseBeanNull
import com.lbb.common.model.exception.ApiException
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by 胡涛.
 */
object RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
    </T> */
    fun <T> rxSchedulerHelper(): FlowableTransformer<T, T> {    //compose简化线程
        return FlowableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 统一线程处理，compose简化线程
     */
    fun <T> transformerIoBean(transformer: LifecycleTransformer<BaseBean<T>>): FlowableTransformer<BaseBean<T>, T> {
        return FlowableTransformer { observable ->
            observable.compose(transformer)
                    .compose(rxSchedulerHelper())
                    .compose(handleResult())
        }
    }

    fun <T> transformerIoAny(transformer: LifecycleTransformer<Any>): FlowableTransformer<Any, T> {
        return FlowableTransformer { observable ->
            observable.compose(transformer)
                    .compose(rxSchedulerHelper())
                    .compose(handleResultAny())
        }
    }

    fun  transformerIoNull(transformer: LifecycleTransformer<*>): FlowableTransformer<Any, String> {
        transformer as LifecycleTransformer<Any>
        return FlowableTransformer { observable ->
            observable.compose(transformer)
                    .compose(rxSchedulerHelper())
                    .compose(handleResultNull())
        }
    }


    /**
     * 结果处理
     */
    private fun <T> handleResult(): FlowableTransformer<BaseBean<T>, T> {
        return FlowableTransformer { httpResponseFlowable ->
            httpResponseFlowable.flatMap { it ->
                if (it.code == "1") {
                    if (it.data != null) {
                        createData(it.data)
                    } else {
                        Flowable.error(ApiException(it.code, it.msg))
                    }
                } else {
                    Flowable.error(ApiException(it.code, it.msg))
                }
            }
        }
    }

    /**
     * 结果处理ANY
     */
    private fun <T> handleResultAny(): FlowableTransformer<Any, T> {
        return FlowableTransformer { httpResponseFlowable ->
            httpResponseFlowable.flatMap { it ->
                it as BaseBean<T>
                if (it.code == "1") {
                    if (it.data != null) {
                        createData(it.data)
                    } else {
                        Flowable.error(ApiException(it.code, it.msg))
                    }
                } else {
                    Flowable.error(ApiException(it.code, it.msg))
                }
            }
        }
    }

    /**
     * 结果处理data为null
     */
    private fun handleResultNull(): FlowableTransformer<Any, String> {
        return FlowableTransformer { httpResponseFlowable ->
            httpResponseFlowable.flatMap { it ->
                it as BaseBeanNull
                if (it.code == "1") {
                    createData(it.msg)
                } else {
                    Flowable.error(ApiException(it.code, it.msg))
                }
            }
        }
    }

    /**
     * 生成Flowable
     */
    private fun <T> createData(t: T): Flowable<T> {
        return Flowable.create({ emitter ->
            try {
                emitter.onNext(t)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }, BackpressureStrategy.BUFFER)
    }

    fun showError(onError: (String) -> Unit, t: Throwable) {
        if (t is ApiException) {
            onError.invoke(t.msg)
        } else {
            onError.invoke("获取数据失败")
            Log.e("RxUtil", t.message)
        }
    }
}