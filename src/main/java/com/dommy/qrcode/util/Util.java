package com.dommy.qrcode.util;

import android.content.Context;
import android.os.Looper;

import java.util.Random;


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
        return s;
    }
    public static void resString(String str) throws Exception {

    }
}
