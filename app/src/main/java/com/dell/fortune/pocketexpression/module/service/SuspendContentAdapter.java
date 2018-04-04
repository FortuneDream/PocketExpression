package com.dell.fortune.pocketexpression.module.service;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;

/**
 * Created by 81256 on 2018/3/28.
 */

public class SuspendContentAdapter extends BaseQuickAdapter<LocalExpressionItem, SuspendContentAdapter.MyViewHolder> {


    public SuspendContentAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MyViewHolder helper, LocalExpressionItem item) {

    }

    class MyViewHolder extends BaseViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }
}
