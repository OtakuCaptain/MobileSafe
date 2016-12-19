package com.chen.mobilesafe.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.activity.R;
import com.chen.mobilesafe.utils.ConstantValue;
import com.chen.mobilesafe.utils.Md5Util;
import com.chen.mobilesafe.utils.SpUtil;
import com.chen.mobilesafe.utils.ToastUtil;

public class HomeActivity extends Activity {

    private GridView gv_home;
    private String[] mTitleStr;
    private int[] mDrawableIds;
    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = this;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            //初始化UI
            initUI();
            //初始化数据
            initData();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //初始化UI
                    initUI();
                    //初始化数据
                    initData();
                } else {
                    ToastUtil.show(getApplicationContext(), "没有此权限，将无法继续使用本软件的某些功能");
                    //初始化UI
                    initUI();
                    //初始化数据
                    initData();
                }
                break;
        }
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
        String psd = SpUtil.getString(mContext, ConstantValue.MOBILE_SAFE_PSD, "");
        if (TextUtils.isEmpty(psd)) {
            //提示设置密码
            showSetPsdDialog();

        } else {
            //确认密码
            showConfirmPsdDialog();
        }
    }

    /**
     * 确认密码的对话框
     */
    private void showConfirmPsdDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final AlertDialog dialog = builder.create();
        final View view = View.inflate(mContext, R.layout.dialog_confirm_psd, null);
        dialog.setView(view);
        dialog.show();
//findViewById因为是view页面，应该用view.findViewById
        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_confirm_psd = (EditText) view.findViewById(R.id.et_confirm_psd);

                String confirm_psd = et_confirm_psd.getText().toString();

                if (TextUtils.isEmpty(confirm_psd)) {
                    ToastUtil.show(mContext, "请输入密码");
                } else {
                    String psd = SpUtil.getString(mContext, ConstantValue.MOBILE_SAFE_PSD, "");
                    if (Md5Util.encoder(confirm_psd).equals(psd)) {
                        Intent intent = new Intent(mContext, SetupOverActivity.class);
                        startActivity(intent);
                        //跳转之后应该关闭对话框
                        dialog.dismiss();
                    } else {
                        ToastUtil.show(mContext, "密码不正确");
                    }
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 初始设置密码的对话框
     */
    private void showSetPsdDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final AlertDialog dialog = builder.create();
        final View view = View.inflate(mContext, R.layout.dialog_set_psd, null);
        dialog.setView(view);
        dialog.show();
//findViewById因为是view页面，应该用view.findViewById
        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_psd);
                EditText et_confirm_psd = (EditText) view.findViewById(R.id.et_confirm_psd);

                //读取编辑文本框的数据
                String psd = et_set_psd.getText().toString();
                String confirm_psd = et_confirm_psd.getText().toString();
                //确认两次密码是否一致
                if (TextUtils.isEmpty(psd) || TextUtils.isEmpty(confirm_psd)) {
                    ToastUtil.show(mContext, "请输入密码");
                } else {
                    if (psd.equals(confirm_psd)) {
                        SpUtil.putString(mContext, ConstantValue.MOBILE_SAFE_PSD, psd);
//                        Intent intent = new Intent(mContext, TestActivity.class);
                        Intent intent = new Intent(mContext, SetupOverActivity.class);
                        startActivity(intent);
                        //跳转之后应该关闭对话框
                        dialog.dismiss();
                        SpUtil.putString(mContext, ConstantValue.MOBILE_SAFE_PSD, Md5Util.encoder(confirm_psd));

                    } else {
                        ToastUtil.show(mContext, "两次密码不一致");
                    }
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
