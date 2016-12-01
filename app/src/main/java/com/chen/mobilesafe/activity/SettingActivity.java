package com.chen.mobilesafe.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;


import com.chen.activity.R;
import com.chen.mobilesafe.utils.ConstantValue;
import com.chen.mobilesafe.utils.SpUtil;
import com.chen.mobilesafe.view.SettingItemView;

public class SettingActivity extends Activity implements View.OnClickListener {

    private Context mContext;
    private SettingItemView siv_update;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mContext=this;
        //版本更新开关
        initUpdate();

    }

    /**
     * 版本更新开关
     */
    private void initUpdate() {
        siv_update = (SettingItemView) findViewById(R.id.siv_update);
        boolean open_update = SpUtil.getBoolean(mContext, ConstantValue.OPEN_UPDATE,false );
        siv_update.setCheck(open_update);
        siv_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.siv_update:
                boolean isCheck = siv_update.isCheck();
                siv_update.setCheck(!isCheck);
                SpUtil.putBoolean(mContext,ConstantValue.OPEN_UPDATE,!isCheck);
                break;
        }
    }
}
