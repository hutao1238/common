package com.lbb.common.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    private final String CIPHERMODEPADDING = "AES/CBC/PKCS5Padding";// AES/CBC/PKCS7Padding

    private SecretKeySpec skforAES = null;
    private static String ivParameter = "A-16-Byte-String";// 密钥默认偏移，可更改

    private byte[] iv = ivParameter.getBytes();
    private IvParameterSpec IV;
    String sKey = "lxSvsWSDK/Hdg+QH";// key必须为16位，可更改为自己的key

    private static AES instance = null;

    public static AES getInstance() {
        if (instance == null) {
            synchronized (AES.class) {
                if (instance == null) {
                    instance = new AES();
                }
            }
        }
        return instance;
    }

    private AES() {
        IV = new IvParameterSpec(iv);
    }

    public String encrypt(byte[] plaintext) {
        getSecretKey();
        byte[] ciphertext = encrypt(CIPHERMODEPADDING, skforAES, IV, plaintext);
        return Base64.encodeToString(ciphertext, Base64.DEFAULT);
    }

    public String getsKey() {
        return sKey;
    }

    private void getSecretKey() {
        try {
            sKey = getRandomString(16);
            byte[] skAsByteArray = sKey.getBytes("ASCII");
            skforAES = new SecretKeySpec(skAsByteArray, "AES");
        } catch (Exception ignored) {

        }
    }

    public String decrypt(String ciphertext_base64) {
        byte[] s = Base64.decode(ciphertext_base64, Base64.DEFAULT);
        return new String(decrypt(CIPHERMODEPADDING, skforAES, IV,
                s));
    }

    private byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV,
                           byte[] msg) {
        try {
            Cipher c = Cipher.getInstance(cmp);
            c.init(Cipher.ENCRYPT_MODE, sk, IV);
            return c.doFinal(msg);
        } catch (Exception ignored) {
        }
        return null;
    }

    private byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV,
                           byte[] ciphertext) {
        try {
            Cipher c = Cipher.getInstance(cmp);
            c.init(Cipher.DECRYPT_MODE, sk, IV);
            return c.doFinal(ciphertext);
        } catch (Exception ignored) {
        }
        return null;
    }

    private static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ|";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
