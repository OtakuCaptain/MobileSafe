package com.chen.mobilesafe.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.ListView;

import com.chen.activity.R;




public class ContactListActivity extends Activity {

    private ListView lv_contact;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        setContentView(R.layout.activity_contact_list);
        initUI();
        initData();
    }

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                ContentResolver contentResolver = getContentResolver();
                //查询系统联系人数据库，需要权限
                Cursor cursor = contentResolver.query(
                        Uri.parse("content://com.android.contacts/raw_contacts"),
                        new String[]{"contact_id"},
                        null, null, null);
                assert cursor != null;
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0);
                    Log.i("hehe", "id=" + id);
                    Cursor indexCursor = contentResolver.query(
                            Uri.parse("content://com.android.contacts/data"),
                            new String[]{"data1", "mimetype"},
                            "raw_contact_id=?", new String[]{id}, null);
                    assert indexCursor != null;
                    while (indexCursor.moveToNext()) {
                        Log.i("hehe", "data" + indexCursor.getString(0));
                        Log.i("hehe", "mimetype" + indexCursor.getString(1));

                    }
                    indexCursor.close();
                }
                cursor.close();
            }
        }).start();


    }

    private void initUI() {
        lv_contact = (ListView) findViewById(R.id.lv_contact);

    }


}
