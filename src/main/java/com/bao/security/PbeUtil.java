package com.bao.security;

import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by user on 16/10/25.
 */
public class PbeUtil {

    public static String test = "test";

    public static void jdkPbe() throws Exception {
        //初始化盐
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = secureRandom.generateSeed(8);

        //口令与密钥
        String password = "baochunyu";
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
        Key key = factory.generateSecret(pbeKeySpec);

        //加密

        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
        Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
        cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
        byte[] result = cipher.doFinal(test.getBytes());
        System.out.println("result:" + Base64.toBase64String(result)); //转成16进制

        //解密
        cipher.init(Cipher.DECRYPT_MODE, key , pbeParameterSpec);
        byte[] origin = cipher.doFinal(result);
        System.out.println("origin:" + new String(origin));
    }

    public static void main(String args[]) throws Exception {
        jdkPbe();
    }
}
