/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home.collection;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dell.fortune.pocketexpression.app.R;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;
import com.dell.fortune.pocketexpression.util.common.FrescoProxy;
import com.dell.fortune.tools.LogUtils;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 81256 on 2018/4/3.
 */

public class HomeCollectionAdapter extends BaseQuickAdapter<LocalExpressionItem, BaseViewHolder> {


    public HomeCollectionAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalExpressionItem item) {
        SimpleDraweeView picSdv = helper.getView(R.id.pic_sdv);
        FrescoProxy.showLocalSimpleView(picSdv, item.getPath());
        LogUtils.e(item.getPath());
    }
}
