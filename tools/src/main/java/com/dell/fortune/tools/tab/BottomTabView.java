/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.tools.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dell.fortune.tools.R;

public class BottomTabView extends LinearLayout {
    private TextView tv;
    private AppCompatImageView ico;
    private int pressResource;
    private int normalResource;
    private int pressTextColor;
    private int normalTextColor;

    public BottomTabView(Context context) {
        this(context, null);
    }

    public BottomTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }


    private void initView(AttributeSet attrs) {
        inflate(getContext(), R.layout.tools_view_bottom_tab, this);
        setGravity(Gravity.CENTER);
        ico = getView(R.id.ico_iv);
        tv = getView(R.id.name_tv);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.tools_BottomTabView);
        pressResource = array.getResourceId(R.styleable.tools_BottomTabView_tools_tab_press_ico, 0);
        normalResource = array.getResourceId(R.styleable.tools_BottomTabView_tools_tab_normal_ico, 0);
        String name = array.getString(R.styleable.tools_BottomTabView_tools_tab_name);
        normalTextColor = array.getColor(R.styleable.tools_BottomTabView_tools_tab_normal_text_color, ContextCompat.getColor(getContext(), R.color.tools_light_black));
        pressTextColor = array.getColor(R.styleable.tools_BottomTabView_tools_tab_press_text_color, ContextCompat.getColor(getContext(), R.color.tools_mi_green));
        ico.setImageResource(normalResource);
        tv.setText(name);
        array.recycle();
    }

    public void onSelect(Boolean isSelect) {
        if (isSelect) {
            ico.setImageResource(pressResource);
            tv.setTextColor(pressTextColor);
        } else {
            tv.setTextColor(normalTextColor);
            ico.setImageResource(normalResource);
        }
    }


    public <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }
}