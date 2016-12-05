package com.chen.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chen.activity.R;
import com.chen.mobilesafe.utils.ConstantValue;
import com.chen.mobilesafe.utils.SpUtil;

import java.lang.reflect.AccessibleObject;

/**
 * Created by chen on 2016-12-05.
 */
public class Setup4Activity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
    }

    public void prePage(View v){
        Intent intent = new Intent(this, Setup3Activity.class);
        startActivity(intent);
        finish();

    }

    public void nextPage(View v){
        Intent intent = new Intent(this, SetupOverActivity.class);
        startActivity(intent);
        finish();

        SpUtil.putBoolean(this, ConstantValue.SETUP_OVER,true);
    }
}
