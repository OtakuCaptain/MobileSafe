package com.chen.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.activity.R;


public class SettingItemView extends RelativeLayout {

    protected static final String tag = "SettingItemView";
    private CheckBox cb_box;
    private TextView tv_des;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.setting_item_view, this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_des = (TextView) findViewById(R.id.tv_des);
        cb_box = (CheckBox) findViewById(R.id.cb_box);

        //获取自定义属性AttributeSet attrs
        initAttrs(attrs);

    }

    /**
     * 返回属性集合中自定义属性属性值
     * @param attrs  构造方法中维护好的属性集合
     */
    private void initAttrs(AttributeSet attrs) {


        Log.i(tag,"attrs.getAttributeCount():"+attrs.getAttributeCount());
    }

    public boolean isCheck() {
        return cb_box.isChecked();
    }

    public void setCheck(boolean isCheck){
        cb_box.setChecked(isCheck);
        if (isCheck){
            tv_des.setText("自动更新已开启");
        }else {
            tv_des.setText("自动更新已关闭");
        }
    }

}
