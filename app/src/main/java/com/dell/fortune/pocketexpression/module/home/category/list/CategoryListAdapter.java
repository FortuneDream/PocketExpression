package com.dell.fortune.pocketexpression.module.home.category.list;

import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.pocketexpression.util.common.LogUtils;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 81256 on 2018/3/26.
 */

public class CategoryListAdapter extends BaseQuickAdapter<ExpressionItem, CategoryListAdapter.MyHolder> {

    public CategoryListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MyHolder helper, ExpressionItem item) {
        SimpleDraweeView picSdv = helper.getView(R.id.pic_sdv);
        picSdv.setImageURI(Uri.parse(item.getUrl()));
        LogUtils.e(item.getUrl());
    }

    class MyHolder extends BaseViewHolder {

        public MyHolder(View view) {
            super(view);
        }
    }
}
