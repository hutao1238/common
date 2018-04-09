package com.lbb.common.model.exception

import java.lang.Exception

/**
 * Created by 胡涛.
 */
data class ApiException(var code: String, var msg: String) : Exception()