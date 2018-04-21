package com.dell.fortune.pocketexpression.util.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewFlipper;

public class ViewFlipperRollView extends ViewFlipper implements View.OnClickListener, View.OnTouchListener {
    private GestureDetector mGestureDetector;
    private ViewFlipperRollAdapter mAdapter;
    private LayoutInflater mInflater;
    private boolean isFlag = false;
    private OnNoticeClickListener onNoticeClickListener;


    //每个View的点击监听
    public interface OnNoticeClickListener {
        void onNoticeClick(int position);
    }

    public void setOnNoticeClickListener(OnNoticeClickListener onNoticeClickListener) {
        this.onNoticeClickListener = onNoticeClickListener;
    }

    public ViewFlipperRollView(Context context) {
        this(context, null);
    }

    public ViewFlipperRollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mInflater = LayoutInflater.from(getContext());
        mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener());
        setOnTouchListener(this);
    }

    public void setAdapter(ViewFlipperRollAdapter mAdapter) {
        this.mAdapter = mAdapter;
        if (mAdapter != null) {
            removeAllViews();
            int count = mAdapter.getCount();
            for (int i = 0; i < count; i++) {
                View view = mAdapter.getView(i, this, mInflater);
                view.setOnClickListener(this);
                view.setTag(i);//标志第几个
                addView(view, i);//没有Param
            }
        }
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        if (onNoticeClickListener != null) {
            if (!isFlag) {
                onNoticeClickListener.onNoticeClick(tag);
            } else {
                isFlag = false;
            }
        }
    }


    //如果我们需要需要翻页，然后再加上点击事件，这个时候就会有一个问题：
    //ViewFlipper的onClick和onTouch的冲突事件
//   1：在onDown中设置this.flipper.setClickable(true); 然后在onFling方法中this.flipper.setClickable(false);


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        return mGestureDetector.onTouchEvent(event);
    }


    public boolean onTouchEvent(MotionEvent event) {
        // 执行touch 事件
        super.onTouchEvent(event);
        return mGestureDetector.onTouchEvent(event);
    }


    //这个方法会先执行，当返回为true时，才执行onTouchEvent 方法
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //先执行滑屏事件
        mGestureDetector.onTouchEvent(ev);
        super.dispatchTouchEvent(ev);
        return true;
    }

    //以上？？？？？

    private class OnFlipperRollListener extends GestureDetector.SimpleOnGestureListener {
        private static final int FLING_MIN_DISTANCE = 80;
        private static final int FLING_MIN_VELOCITY = 120;

        @Override
        public boolean onDown(MotionEvent e) {
            setClickable(true);
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //左滑
            setClickable(false);
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                showNext();
                //动画
                isFlag = true;
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                showPrevious();
                //动画
                isFlag = true;
            } else {
                isFlag = false;
            }
            return false;
        }
    }
}
