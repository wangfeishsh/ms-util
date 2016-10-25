package com.bao.security;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Security;

/**
 * Created by user on 16/10/25.
 */
public class DesUtil {

    public static String test = "test";


    public static void jdkDes() throws Exception {
        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56); //默认长度
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] encoded = secretKey.getEncoded();

        //key转换
        DESKeySpec desKeySpec = new DESKeySpec(encoded);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        SecretKey convert = factory.generateSecret(desKeySpec);

        //加密
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//加密方式/工作方式/填充方式
        cipher.init(Cipher.ENCRYPT_MODE, convert);
        byte[] result = cipher.doFinal(test.getBytes());
        System.out.println("result:" + Hex.encodeHexString(result)); //转成16进制

        //解密
        cipher.init(Cipher.DECRYPT_MODE, convert);
        byte[] origin = cipher.doFinal(result);
        System.out.println("origin:" + new String(origin));

    }

    public static void bcDes() throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES", "BC");//添加provider
        keyGenerator.init(56); //默认长度
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] encoded = secretKey.getEncoded();

        //key转换
        DESKeySpec desKeySpec = new DESKeySpec(encoded);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        SecretKey convert = factory.generateSecret(desKeySpec);

        //加密
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//加密方式/工作方式/填充方式
        cipher.init(Cipher.ENCRYPT_MODE, convert);
        byte[] result = cipher.doFinal(test.getBytes());
        System.out.println("result:" + Hex.encodeHexString(result)); //转成16进制

        //解密
        cipher.init(Cipher.DECRYPT_MODE, convert);
        byte[] origin = cipher.doFinal(result);
        System.out.println("origin:" + new String(origin));

    }


    public static void main(String args[]) throws Exception {
        jdkDes();
        bcDes();
    }
}
