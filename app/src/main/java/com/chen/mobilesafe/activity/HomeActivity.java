package com.chen.mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.activity.R;
import com.chen.mobilesafe.utils.ConstantValue;
import com.chen.mobilesafe.utils.SpUtil;

public class HomeActivity extends Activity {

    private GridView gv_home;
    private String[] mTitleStr;
    private int[] mDrawableIds;
    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext=this;

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
        //设置条目的点击事件
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showDialog();
                        break;
                    case 8:
                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void showDialog() {
        //判断本地是否有密码
        String psd = SpUtil.getString(mContext, ConstantValue.MOBILE_SDAFE_PSD, "");
        if(TextUtils.isEmpty(psd)){
            //提示设置密码
            showSetPsdDialog();
        }else {
            //确认密码
            showConfirmPsdDialog();

        }
    }

    /**
     * 确认密码的对话框
     */
    private void showConfirmPsdDialog() {

    }

    /**
     * 初始设置密码的对话框
     */
    private void showSetPsdDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        AlertDialog dialog = builder.create();
        View view = View.inflate(mContext, R.layout.dialog_set_psd, null);
        dialog.setView(view);
        dialog.show();
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
