package com.lbb.common.utils

import com.google.gson.Gson
import com.lbb.common.app.App


/**
 * Created by 胡涛.
 */
object EncryptUtil {
    fun encrypt(map: Map<String,String>):Map<String,String>{
        val json = Gson().toJson(map)
        val encrypt = AES.getInstance().encrypt(json.toByteArray())
        val privateKey = AES.getInstance().getsKey()
        val stream = App.instance?.assets?.open("rsa_public_key.pem")
        val privateKeyEncrypt = RSA.encryptDataByPublicKey(privateKey.toByteArray() , RSA.loadPublicKey(stream))
        return mapOf(Pair("data",encrypt), Pair("key_str",privateKeyEncrypt))
    }
}