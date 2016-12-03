package com.chen.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.chen.activity.R;
import com.chen.mobilesafe.utils.ConstantValue;
import com.chen.mobilesafe.utils.SpUtil;


public class SetupOverActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean setup_over = SpUtil.getBoolean(this, ConstantValue.SETUP_OVER, false);
        if (setup_over){
            //密码输入成功，且设置完成导航界面-->停留在功能列表界面
            setContentView(R.layout.activity_setup_over);
        }else {
            //密码输入成功，但是没有设置完成导航界面-->跳转到导航界面1
            Intent intent = new Intent(this, Setup1Activity.class);
            startActivity(intent);
            //开启新界面以后，关闭功能列表界面
            finish();
        }
    }
}
