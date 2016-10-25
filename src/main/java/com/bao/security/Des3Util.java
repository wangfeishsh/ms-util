package com.bao.security;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jcajce.provider.symmetric.DESede;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.security.SecureRandom;
import java.security.Security;

/**
 * Created by user on 16/10/25.
 */
public class Des3Util {

    public static String test = "test";


    public static void jdk3Des() throws Exception {
        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
        keyGenerator.init(168); //默认长度
        //keyGenerator.init(new SecureRandom()); //可以根据不同算法计算出默认长度
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] encoded = secretKey.getEncoded();

        //key转换
        DESedeKeySpec desKeySpec = new DESedeKeySpec(encoded);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        SecretKey convert = factory.generateSecret(desKeySpec);

        //加密
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");//加密方式/工作方式/填充方式
        cipher.init(Cipher.ENCRYPT_MODE, convert);
        byte[] result = cipher.doFinal(test.getBytes());
        System.out.println("result:" + Hex.encodeHexString(result)); //转成16进制

        //解密
        cipher.init(Cipher.DECRYPT_MODE, convert);
        byte[] origin = cipher.doFinal(result);
        System.out.println("origin:" + new String(origin));

    }

    public static void bc3Des() throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede", "BC");//添加provider
        keyGenerator.init(168); //默认长度
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] encoded = secretKey.getEncoded();

        //key转换
        DESedeKeySpec desKeySpec = new DESedeKeySpec(encoded);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        SecretKey convert = factory.generateSecret(desKeySpec);

        //加密
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");//加密方式/工作方式/填充方式
        cipher.init(Cipher.ENCRYPT_MODE, convert);
        byte[] result = cipher.doFinal(test.getBytes());
        System.out.println("result:" + Hex.encodeHexString(result)); //转成16进制

        //解密
        cipher.init(Cipher.DECRYPT_MODE, convert);
        byte[] origin = cipher.doFinal(result);
        System.out.println("origin:" + new String(origin));

    }


    public static void main(String args[]) throws Exception {
        jdk3Des();
        bc3Des();
    }
}
