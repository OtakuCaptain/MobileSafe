package com.chen.mobilesafe.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {

    private static SharedPreferences sp;

    /**
     * 写入boolean变量标志
     * @param context 上下文
     * @param key     存储节点名称
     * @param value   存储节点的值
     */
    public static void putBoolean(Context context, String key, boolean value){
        if (sp==null) {
//(存储结点文件名称，读写方式)
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).apply();
    }

    /**
     * 读取boolean变量标志
     * @param context 上下文
     * @param key     存储节点名称
     * @param defValue   节点的默认值
     * @return          默认值或读取到的值
     */
    public static boolean getBoolean(Context context, String key, boolean defValue){
        if (sp==null) {
//(存储结点文件名称，读写方式)
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defValue);
    }

    /**
     * 存储密码
     * @param context 上下文
     * @param key     存储节点名称
     * @param value   存储节点的值String
     */
    public static void putString(Context context, String key, String value){
        if (sp==null) {
//(存储结点文件名称，读写方式)
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).apply();
    }

    /**
     * 读取密码
     * @param context 上下文
     * @param key     存储节点名称
     * @param defValue   节点的默认值
     * @return          默认值或读取到的值
     */
    public static String getString(Context context, String key, String defValue){
        if (sp==null) {
//(存储结点文件名称，读写方式)
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key,defValue);
    }

}
