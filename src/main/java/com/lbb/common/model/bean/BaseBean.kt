package com.lbb.common.model.bean

/**
 * Created by 胡涛.
 */
data class BaseBean<T>(var code: String, var msg: String, var data: T)