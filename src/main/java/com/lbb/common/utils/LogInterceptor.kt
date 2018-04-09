package com.lbb.common.utils

import okhttp3.*
import okio.Buffer
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.net.URLDecoder

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
/**
 * Created by 胡涛 on 2017/7/13.
 * 作用：日志拦截器
 */
class LogInterceptor : Interceptor, AnkoLogger {
    val TAG = "HttpUtil"
    var flag = "开始请求"
    var sign = "======================="
    override fun intercept(chain: Interceptor.Chain?): Response? {
        val request = chain?.request()
    //    logForRequest(request)
        val response = chain?.proceed(request)
        return logForResponse(response)
    }

    /**
     * 打印响应体
     */
    private fun logForResponse(response: Response?): Response? {
        val copy = response?.newBuilder()?.build()
        val resp = copy?.body()?.string()
        flag = "响应开始"
        sign = "*-*-*-*-*-*-*-*-*-*-*-*"
        error { "$sign$flag$sign" }
        error { "请求地址：${copy?.request()?.url()}" }
        if(copy?.request()?.body()?.contentType()?.type().equals("multipart")){
            val body = ResponseBody.create(MediaType.parse("multipart/form-data"), resp)
            return response?.newBuilder()?.body(body)?.build()
        }
        val buffer = Buffer()
        copy?.request()?.body()?.writeTo(buffer)
        error { "对应的请求体：${URLDecoder.decode(buffer.readUtf8(), "utf-8")}" }
        error { "响应码：${copy?.code()}" }
        error { "响应内容：${resp}" }
        val body = ResponseBody.create(MediaType.parse("application/json"), resp)
        flag = "响应结束"
        error { "$sign$flag$sign" }
        return response?.newBuilder()?.body(body)?.build()
    }

    /**
     * 打印请求体
     */
    private fun logForRequest(request: Request?) {
        val copy = request?.newBuilder()?.build()
        flag = "请求开始"
        error { "$sign$flag$sign" }
        error { "请求地址：${copy?.url()}" }
        error { "请求方式：${copy?.method()}" }
        val buffer = Buffer()
        copy?.body()?.writeTo(buffer)
        val requestContent = URLDecoder.decode(buffer.readUtf8(), "utf-8")
        error { "请求体：$requestContent" }
        flag = "请求结束"
        error { "$sign$flag$sign" }
    }

}