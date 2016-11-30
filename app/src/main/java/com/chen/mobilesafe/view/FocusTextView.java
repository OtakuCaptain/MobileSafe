package com.chen.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * 能够获取焦点的自定义控件
 */
public class FocusTextView extends TextView {
    public FocusTextView(Context context) {
        super(context);
    }

    public FocusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //重写获取焦点的方法
    @Override
    public boolean isFocused() {
        return true;
    }
}
