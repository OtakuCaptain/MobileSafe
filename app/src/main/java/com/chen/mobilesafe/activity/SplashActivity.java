package com.chen.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.activity.R;
import com.chen.mobilesafe.utils.StreamUtil;
import com.chen.mobilesafe.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {
    protected static final String tag = "SplashActivity";
    /**
     * 更新版本状态码
     */
    private static final int UPDATE_VERSION = 100;

    /**
     * 直接进入主界面状态码
     */
    private static final int ENTER_HOME = 101;
    /**
     * 网址错误状态码
     */
    private static final int URL_ERROR = 102;
    /**
     * IO错误状态码
     */
    private static final int IO_ERROR = 103;
    /**
     * JSON错误状态码
     */
    private static final int JSON_ERROR = 104;

    private TextView tv_version_name;
    private int mLocalVersionCode;
    private Context mContext;
    private String mVersionDes;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case UPDATE_VERSION:
                    showUpdateDialog();
                    break;
                case ENTER_HOME:
                    enterHome();
                    break;
                case URL_ERROR:
                    ToastUtil.show(mContext,"url异常");
                    enterHome();
                    break;
                case IO_ERROR:
                    ToastUtil.show(mContext,"读取异常");
                    enterHome();
                    break;
                case JSON_ERROR:
                    ToastUtil.show(mContext,"json解析异常");
                    enterHome();
                    break;
            }
        }
    };


    private void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("更新提示");
        builder.setMessage(mVersionDes);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });


        builder.show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext=this;

//        初始化UI
        initUI();
//       初始化数据
        initData();
    }

    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 初始化数据
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

                Message msg = Message.obtain();
                long startTime = System.currentTimeMillis();
                try {
                    //封装URL地址
                    URL url = new URL("http://192.168.0.118:8080/mobilesafe.json");
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

                        Log.i(tag, json);

//                        解析json
                        JSONObject jsonObject = new JSONObject(json);

                        String versionName = jsonObject.getString("versionName");
                        mVersionDes = jsonObject.getString("versionDes");
                        String versionCode = jsonObject.getString("versionCode");
                        String downloadUrl = jsonObject.getString("downloadUrl");

                        Log.i(tag, versionName);
                        Log.i(tag, mVersionDes);
                        Log.i(tag, versionCode);
                        Log.i(tag, downloadUrl);

                        if (mLocalVersionCode < Integer.parseInt(versionCode)) {
                            //提示用户更新
                            msg.what = UPDATE_VERSION;

                        } else {
                            //进入主界面
                            msg.what = ENTER_HOME;

                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    msg.what = URL_ERROR;

                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what = IO_ERROR;

                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.what = JSON_ERROR;
                } finally {

                    //指定睡眠时间4s，网络请求超4s则不做处理
                    //网络请求不超过4s则补足4s

                    long endTime = System.currentTimeMillis();
                    if(endTime-startTime<4000){
                        try {
                            Thread.sleep(4000-(endTime-startTime));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(msg);
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
