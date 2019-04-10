package com.dommy.qrcode.util;

import android.content.Context;
import android.os.Looper;
import android.util.Base64;

import com.dommy.qrcode.util.pass.Base64Utils;
import com.dommy.qrcode.util.pass.RSAUtils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Util {
    private final static String HEX = "0123456789ABCDEF";
    private static final int keyLenght = 16;
    private static final String defaultV = "0";
    private static String PRIVATE_KEY="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoG" +
            "BAJNt6804qqX/R/MZZrn0m1F2dK349pboZeGILuNWYdS17h/fgPKqzrDVxVijZJa89j9kPJ8Q" +
            "oeWyvZWLxXNbwg5GpJbf+sNlrbL553u5RtiGaJBocjtAg0UDzMVY7bXsO7VVr2oNTVA5TnYLQ" +
            "sNK3T6HiRlbeuIVJP4g54+SNG63AgMBAAECgYBZpa6MFNgXplpu5VRE4QuNBpW+sDPp4ZkKAh" +
            "BY5s8IVDRfJz420Uxr1SFyW7CvxGmMdHw0UR3RGdYUWHCRTAt8TBGe5fSblLym98cUBHUlXZG" +
            "LBnNP6wCDWS4/rg7b728PF07m/c9Ar+2C3uchJDLKP9waPBzkJoX4AW0Z1/BdcQJBAM8pOtrT" +
            "uIp6vgnuU/e5qUlGNgx/jgMM06uJpFIEF2oMOzA+YdAqtdUqKlws4vv+75jHsatjKKYLdUi1J" +
            "mQewZkCQQC2L7YjQhpchIX3ugWxZO7uQV7MU3UPkT27ACduABVtKpkWhM89uyR8N6b4wvFoDp" +
            "ti1WiV+N/83LwumRvFe4TPAkBkXg1tx/Y44Y8BML7t3r3uCl5VX9dmEujayy0Zr3HIp3Rlw1O" +
            "Qj6DZTjyHvBedD771voqOBtTC3fpM57dZ+qzBAkAai5chSgdnRZPzhXJA57D8nB/A68QZsZLq" +
            "BVq5Z1+32UYXBuDfYL4vLziv9E+SVtaDLw8fEvUn9c2hiIOIYbf3AkBdPkiChYkgwlOnnbmBt" +
            "e424bwWhAnUdq0d69gcRRD9HN7SMbiQyvaCUb991T7OQMyJFT0q8SRxCO2vSgPTTCHQ";

    private static String PUCLIC_KEY ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7D45WCFD" +
            "y1CiFAs2CDJHL9OZpzMkvTMmXxpSXhmnv4LgcmM6l4py3Met8kl9GeOB9Kvlfd2urrx/X4EwQ" +
            "3YuM1AjAcyUQzcplJNybmvv3IaPXdSCb1MpRB26fRDo55NyJbMXAxPv7qciePYUUfzipUo2Hd" +
            "KS1sMDS+Y3rPOKPsQIDAQAB";
    public static void getToast(Context context,String text){
        Looper.prepare();
        android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
    //length用户要求产生字符串的长度
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    public static String getBase64(String str){
        byte[] b = null;
        String s = null;
        b = str.getBytes();
        s = Base64.encodeToString(b, Base64.DEFAULT);
        return s;
    }
    public static String resString(String str) throws Exception {
        return Util.encrypt(PRIVATE_KEY,str);
    }


    /**
     * 加密
     *
     * @param key
     *            密钥
     * @param src
     *            加密文本
     * @return
     * @throws Exception
     */
    public static String encrypt(String key, String src) throws Exception {
        // /src = Base64.encodeToString(src.getBytes(), Base64.DEFAULT);
        byte[] rawKey = toMakekey(key, keyLenght, defaultV).getBytes();// key.getBytes();
        byte[] result = encrypt(rawKey, src.getBytes("utf-8"));
        // result = Base64.encode(result, Base64.DEFAULT);
        return toHex(result);
    }
    /**
     * 真正的加密过程
     * 1.通过密钥得到一个密钥专用的对象SecretKeySpec
     * 2.Cipher 加密算法，加密模式和填充方式三部分或指定加密算 (可以只用写算法然后用默认的其他方式)Cipher.getInstance("AES");
     * @param key
     * @param src
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] key, byte[] src) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encrypted = cipher.doFinal(src);
        return encrypted;
    }
    /**
     * 二进制转字符,转成了16进制
     * 0123456789abcdefg
     * @param buf
     * @return
     */
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
    /**
     * 密钥key ,默认补的数字，补全16位数，以保证安全补全至少16位长度,android和ios对接通过
     * @param str
     * @param strLength
     * @param val
     * @return
     */
    private static String toMakekey(String str, int strLength, String val) {

        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(str).append(val);
                str = buffer.toString();
                strLen = str.length();
            }
        }
        return str;
    }
}
