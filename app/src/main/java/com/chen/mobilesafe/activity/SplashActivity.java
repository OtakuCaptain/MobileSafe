package com.chen.mobilesafe.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.chen.activity.R;
import com.chen.mobilesafe.utils.StreamUtil;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {
    protected static final String tag = "SplashActivity";

    private TextView tv_version_name;
    private int mLocalVersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        初始化UI
        initUI();
//       初始化数据
        initData();
    }
    /**
     *
     */
    private void initData() {
        //应用版本名称
        tv_version_name.setText(getVersionName());
//        获取本地版本号
        mLocalVersionCode = getVersionCode();
        checkCode();
    }
    /**
     * 检测版本号
     */
    private void checkCode() {

        new Thread() {
            public void run() {
                try {
                    //封装URL地址
                    URL url = new URL("http://192.168.162.2:8080/mobilesafe.json");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    //设置请求头
                    //请求超时
                    connection.setConnectTimeout(2000);
                    //读取超时
                    connection.setReadTimeout(2000);
                    //默认请求方式就是GET
                    connection.setRequestMethod("GET");
                    //获取响应码
                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String json = StreamUtil.streamToString(inputStream);

                        Log.i(tag,json);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 获取版本号
     *
     * @return 返回0说明异常
     */
    private int getVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取版本名称
     *
     * @return 返回null说明异常
     */
    private String getVersionName() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化UI的方法
     */
    private void initUI() {
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);
    }


}
