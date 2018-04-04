package com.dell.fortune.pocketexpression.module.user;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;

/**
 * Created by 81256 on 2018/4/3.
 */

public class UserCollectionAdapter extends BaseQuickAdapter<ExpressionItem, UserCollectionAdapter.MyViewHolder> {


    public UserCollectionAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MyViewHolder helper, ExpressionItem item) {

    }

    public class MyViewHolder extends BaseViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }
}
