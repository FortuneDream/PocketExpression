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
    private int selectResource;
    private int noSelectResource;

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
        inflate(getContext(), R.layout.view_bottom_tab, this);
        setGravity(Gravity.CENTER);
        ico = getView(R.id.ico_iv);
        tv = getView(R.id.name_tv);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BottomTabView);
        selectResource = array.getResourceId(R.styleable.BottomTabView_itemTabSelectIco, 0);
        noSelectResource = array.getResourceId(R.styleable.BottomTabView_itemTabNoSelectIco, 0);
        String name = array.getString(R.styleable.BottomTabView_itemTabName);
        ico.setImageResource(noSelectResource);
        tv.setText(name);
        array.recycle();
    }

    public void onSelect(Boolean isSelect) {
        if (isSelect) {
            ico.setImageResource(selectResource);
            tv.setTextColor(ContextCompat.getColor(getContext(), R.color.mi_green));
        } else {
            tv.setTextColor(ContextCompat.getColor(getContext(), R.color.light_black));
            ico.setImageResource(noSelectResource);
        }
    }


    public <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }
}