package com.bao.security;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Created by user on 16/10/24.
 */
public class Base64Util {

    public static String test = "test";

    public static void jdkBase64() throws IOException {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String encode = base64Encoder.encode(test.getBytes());
        System.out.println(encode);

        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] decode = base64Decoder.decodeBuffer(encode);
        System.out.println(new String(decode));

    }

    public static void commonsCodesBase64() throws IOException {
        Base64 base64 = new Base64();
        byte[] encode = base64.encode(test.getBytes());
        System.out.println(new String(encode));

        byte[] decode = base64.decode(encode);
        System.out.println(new String(decode));

    }

    public static void bouncyCastleBase64() throws IOException {
        byte[] encode = org.bouncycastle.util.encoders.Base64.encode(test.getBytes());
        System.out.println(new String(encode));
        byte[] decode = org.bouncycastle.util.encoders.Base64.decode(encode);
        System.out.println(new String(decode));
    }

    public static void main(String args[]) throws IOException {

        jdkBase64();

        commonsCodesBase64();

        bouncyCastleBase64();

    }
}
