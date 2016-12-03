package com.chen.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Md5Util extends Object {
    /**
     * 给字符串按照Md5算法进行加密
     *
     * @param psd 需要加密的密码
     */
    public static String encoder(String psd) {
        try {
            //加盐处理
            psd = psd + "mobilesafe";
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bs = digest.digest(psd.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bs) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                stringBuffer.append(hexString);
                return stringBuffer.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
