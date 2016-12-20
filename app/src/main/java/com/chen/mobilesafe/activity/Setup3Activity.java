package com.chen.mobilesafe.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chen.activity.R;
import com.chen.mobilesafe.utils.ToastUtil;

import static com.chen.mobilesafe.utils.ConstantValue.QUEST_CODE_CONTACTS;
import static com.chen.mobilesafe.utils.ConstantValue.QUEST_CODE_SEND_SMS;

/**
 * Created by chen on 2016-12-05.
 */
public class Setup3Activity extends BaseActivity {

    private EditText et_phone_number;
    private Button bt_select_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        initUI();
    }

    private void initUI() {
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        bt_select_number = (Button) findViewById(R.id.bt_select_number);
        bt_select_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPermissionGranted(Manifest.permission.READ_CONTACTS,QUEST_CODE_CONTACTS)){
                Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
                startActivityForResult(intent, 0);}else {

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void prePage(View v) {
        Intent intent = new Intent(this, Setup2Activity.class);
        startActivity(intent);
        finish();

    }

    public void nextPage(View v) {
        Intent intent = new Intent(this, Setup4Activity.class);
        startActivity(intent);
        finish();

    }
}
