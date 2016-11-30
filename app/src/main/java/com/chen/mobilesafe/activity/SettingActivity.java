package com.chen.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.chen.activity.R;

public class SettingActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_setting);
    }
}
