package com.bao.security;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

/**
 * Created by user on 16/10/25.
 */
public class AesUtil {

    public static String test = "test";


    public static void jdkAes() throws Exception {
        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); //默认长度
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] encoded = secretKey.getEncoded();

        //key转换
        Key key = new SecretKeySpec(encoded,"AES");

        //加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//加密方式/工作方式/填充方式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(test.getBytes());
        System.out.println("result:" + Base64.toBase64String(result));

        //解密
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] origin = cipher.doFinal(result);
        System.out.println("origin:" + new String(origin));

    }

    public static void bcAes() throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "BC");//添加provider
        keyGenerator.init(128); //默认长度
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] encoded = secretKey.getEncoded();

        //key转换
        Key key = new SecretKeySpec(encoded,"AES");

        //加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//加密方式/工作方式/填充方式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(test.getBytes());
        System.out.println("result:" + Base64.toBase64String(result)); //转成16进制

        //解密
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] origin = cipher.doFinal(result);
        System.out.println("origin:" + new String(origin));

    }


    public static void main(String args[]) throws Exception {
        jdkAes();
        bcAes();
    }
}
