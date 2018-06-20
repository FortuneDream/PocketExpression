/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home.category.list;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.pocketexpression.util.common.FrescoProxy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 81256 on 2018/3/26.
 */

public class CategoryListAdapter extends BaseQuickAdapter<ExpressionItem, BaseViewHolder> {

    public CategoryListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, ExpressionItem item) {
        SimpleDraweeView picSdv = helper.getView(R.id.pic_sdv);
        FrescoProxy.showNetSimpleView(picSdv, item.getUrl());
        helper.setVisible(R.id.fab_ll, false)
                .addOnClickListener(R.id.collection_fab)
                .addOnClickListener(R.id.pic_sdv)
                .addOnClickListener(R.id.fab_ll)
                .addOnClickListener(R.id.share_fab);//子控件点击事件
    }
}
