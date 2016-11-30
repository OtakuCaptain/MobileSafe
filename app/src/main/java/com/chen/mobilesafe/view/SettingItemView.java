package com.chen.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.activity.R;


public class SettingItemView extends RelativeLayout {
    public SettingItemView(Context context) {
        this(context,null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.setting_item_view,this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tv_des = (TextView) findViewById(R.id.tv_des);
        CheckBox cb_box = (CheckBox) findViewById(R.id.cb_box);
    }
}
