package com.dell.fortune.pocketexpression.module.home.category;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.model.bean.ExpressionCategory;

/**
 * Created by 81256 on 2018/3/26.
 */

public class HomeCategoryAdapter extends BaseQuickAdapter<ExpressionCategory, BaseViewHolder> {


    public HomeCategoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpressionCategory item) {
            helper.setText(R.id.category_name_tv,item.getName());
    }

    class MyViewHolder extends BaseViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }
}
