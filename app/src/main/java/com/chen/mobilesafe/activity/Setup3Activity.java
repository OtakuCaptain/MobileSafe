package com.chen.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chen.activity.R;

/**
 * Created by chen on 2016-12-05.
 */
public class Setup3Activity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
    }
    public void prePage(View v){
        Intent intent = new Intent(this, Setup2Activity.class);
        startActivity(intent);
        finish();

    }

    public void nextPage(View v){
        Intent intent = new Intent(this, Setup4Activity.class);
        startActivity(intent);
        finish();

    }
}