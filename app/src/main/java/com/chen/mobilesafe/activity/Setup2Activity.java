package com.chen.mobilesafe.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import com.chen.activity.R;
import com.chen.mobilesafe.utils.ConstantValue;
import com.chen.mobilesafe.utils.SpUtil;
import com.chen.mobilesafe.utils.ToastUtil;
import com.chen.mobilesafe.view.SettingItemView;

/**
 * Created by chen on 2016-12-05.
 */
public class Setup2Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        initUI();
    }

    private void initUI() {
        final SettingItemView siv_sim_bound = (SettingItemView) findViewById(R.id.siv_sim_bound);
        //回显
        String sim_number = SpUtil.getString(this, ConstantValue.SIM_NUMBER, "");
        if (TextUtils.isEmpty(sim_number)) {
            siv_sim_bound.setCheck(false);
        } else {
            siv_sim_bound.setCheck(true);
        }

        siv_sim_bound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
                if (ContextCompat.checkSelfPermission(Setup2Activity.this,strings[1])
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Setup2Activity.this, strings, 1);
                } else {
                    //获取原有状态
                    boolean isCheck = siv_sim_bound.isCheck();
                    //将当前状态取反，并存储序列号
                    siv_sim_bound.setCheck(!isCheck);
                    if (!isCheck) {
                        //获取sim卡序列号
                        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                        @SuppressLint("HardwareIds") String simSerialNumber = manager.getSimSerialNumber();
                        SpUtil.putString(getApplicationContext(), ConstantValue.SIM_NUMBER, simSerialNumber);
                    } else {
                        //将序列号节点删除
                        SpUtil.remove(getApplicationContext(), ConstantValue.SIM_NUMBER);
                    }
                }
            }
        });
    }


    public void nextPage(View v) {
        String serialNumber = SpUtil.getString(this, ConstantValue.SIM_NUMBER, "");
//        if (!TextUtils.isEmpty(serialNumber)) {
        Intent intent = new Intent(this, Setup3Activity.class);
        startActivity(intent);
        finish();
//        } else {
//            ToastUtil.show(this, "请绑定sim卡");
//        }
    }

    public void prePage(View v) {
        Intent intent = new Intent(this, Setup1Activity.class);
        startActivity(intent);
        finish();

    }
}
