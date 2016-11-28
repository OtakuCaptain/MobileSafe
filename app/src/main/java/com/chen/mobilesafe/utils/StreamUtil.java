package com.chen.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class StreamUtil {
    /**
     * @param inputStream 流对象
     * @return 流转换成的字符串      返回null代表异常
     */
    public static String streamToString(InputStream inputStream) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int temp = -1;
        try {
            while ((temp = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, temp);
            }
            return bos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
