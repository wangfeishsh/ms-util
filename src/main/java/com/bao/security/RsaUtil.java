package com.bao.security;

import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by user on 16/10/25.
 */
public class RsaUtil {

    public static String test = "test";

    public static void jdkRsa() throws Exception {

        //初始化密钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)keyPair.getPrivate();
        System.out.println(Base64.toBase64String(rsaPublicKey.getEncoded()));
        System.out.println(Base64.toBase64String(rsaPrivateKey.getEncoded()));

        // 2.私钥加密、公钥解密 ---- 加密
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(test.getBytes());
        System.out.println("私钥加密、公钥解密 ---- 加密:" + Base64.toBase64String(result));

        // 3.私钥加密、公钥解密 ---- 解密
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        result = cipher.doFinal(result);
        System.out.println("私钥加密、公钥解密 ---- 解密:" + new String(result));



        // 4.公钥加密、私钥解密 ---- 加密
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        KeyFactory keyFactory2 = KeyFactory.getInstance("RSA");
        PublicKey publicKey2 = keyFactory2.generatePublic(x509EncodedKeySpec2);
        Cipher cipher2 = Cipher.getInstance("RSA");
        cipher2.init(Cipher.ENCRYPT_MODE, publicKey2);
        byte[] result2 = cipher2.doFinal(test.getBytes());
        System.out.println("公钥加密、私钥解密 ---- 加密:" + Base64.toBase64String(result2));

        // 5.私钥解密、公钥加密 ---- 解密
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory5 = KeyFactory.getInstance("RSA");
        PrivateKey privateKey5 = keyFactory5.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher5 = Cipher.getInstance("RSA");
        cipher5.init(Cipher.DECRYPT_MODE, privateKey5);
        byte[] result5 = cipher5.doFinal(result2);
        System.out.println("公钥加密、私钥解密 ---- 解密:" + new String(result5));

    }

    public static void main(String args[]) throws Exception {
        jdkRsa();
    }
}
