package com.chen.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.activity.R;

public class HomeActivity extends Activity {

    private GridView gv_home;
    private String[] mTitleStr;
    private int[] mDrawableIds;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //初始化UI
        initUI();
        //初始化数据
        initData();
    }

    private void initData() {
        mTitleStr = new String[]{
                "手机防盗", "通信卫士", "软件管理", "进程管理", "流量统计",
                "手机杀毒", "缓存清理", "高级工具", "设置中心"
        };
        mDrawableIds = new int[]{
                R.drawable.home_safe, R.drawable.home_callmsgsafe, R.drawable.home_apps,
                R.drawable.home_taskmanager, R.drawable.home_netmanager, R.drawable.home_trojan,
                R.drawable.home_sysoptimize, R.drawable.home_tools, R.drawable.home_settings,
        };
        gv_home.setAdapter(new MyAdapter());
    }

    private void initUI() {
        gv_home = (GridView) findViewById(R.id.gv_home);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mTitleStr.length;
        }

        @Override
        public Object getItem(int position) {
            return mTitleStr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.gridview_item, null);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);

            tv_title.setText(mTitleStr[position]);
            iv_icon.setBackgroundResource(mDrawableIds[position]);
            return view;
        }
    }
}
