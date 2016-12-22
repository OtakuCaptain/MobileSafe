package com.chen.mobilesafe.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chen.activity.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ContactListActivity extends Activity {

    private ListView lv_contact;
    private List<HashMap<String, String>> contactList = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MyAdapter myAdapter = new MyAdapter();
            lv_contact.setAdapter(myAdapter);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                contactList.clear();
                while (cursor.moveToNext()) {
                    String _id = cursor.getString(0);
                    Log.i("hehe", "id=" + _id);
                    Cursor indexCursor = contentResolver.query(
                            Uri.parse("content://com.android.contacts/data"),
                            new String[]{"data1", "mimetype"},
                            "raw_contact_id= ?", new String[]{_id}, null);
                    HashMap<String, String> hashMap = new HashMap<>();
                    while (indexCursor.moveToNext()) {
                        String data = indexCursor.getString(0);
                        String type = indexCursor.getString(1);
                        Log.i("hehe", "data:  " + data);
                        Log.i("hehe", "mimetype:   " + type);
                            if (type.equals("vnd.android.cursor.item/phone_v2")) {
                                hashMap.put("phone", data);
                            } else if (type.equals("vnd.android.cursor.item/name")) {
                                hashMap.put("name", data);
                            }
                    }
                    indexCursor.close();
                    contactList.add(hashMap);
                }
                cursor.close();
//                Message msg=Message.obtain();
                mHandler.sendEmptyMessage(0);
            }
        }
        ).start();


    }

    private void initUI() {
        lv_contact = (ListView) findViewById(R.id.lv_contact);
    }


    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return contactList.size();
        }

        @Override
        public HashMap<String, String> getItem(int position) {
            return contactList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint("ViewHolder") View view = View.inflate(getApplicationContext(), R.layout.listview_contact_item, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            tv_name.setText(getItem(position).get("name"));
            tv_phone.setText(getItem(position).get("phone"));
            return view;
        }
    }
}
