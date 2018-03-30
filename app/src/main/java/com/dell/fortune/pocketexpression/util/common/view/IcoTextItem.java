package com.dell.fortune.pocketexpression.util.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dell.fortune.pocketexpression.R;


/**
 * Created by 鹏君 on 2016/10/17.
 */
//假如继承LinearLayout会不会报错？
public class IcoTextItem extends RelativeLayout {
    private TextView mTitleTv;
    private AppCompatImageView mIco;

    private String mTitle;
    private int icoId;

    public IcoTextItem(Context context) {
        this(context, null);
    }

    public IcoTextItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IcoTextItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.item_ico_text, this);
        mTitleTv = getView(R.id.title_tv);
        mIco = getView(R.id.ico);

        TypedArray types = getContext().obtainStyledAttributes(attrs, R.styleable.IcoTextItem);
        mTitle = types.getString(R.styleable.IcoTextItem_itemTitleText);
        icoId = types.getResourceId(R.styleable.IcoTextItem_itemTitleIco, 0);
        types.recycle();

        //初始化文字
        mTitleTv.setText(mTitle);
        mIco.setImageResource(icoId);
    }



    public <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }


}
