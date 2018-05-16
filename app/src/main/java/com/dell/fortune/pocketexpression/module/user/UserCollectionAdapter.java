package com.dell.fortune.pocketexpression.module.user;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;
import com.dell.fortune.pocketexpression.util.common.FrescoProxy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 81256 on 2018/4/3.
 */

public class UserCollectionAdapter extends BaseQuickAdapter<LocalExpressionItem, UserCollectionAdapter.MyViewHolder> {


    public UserCollectionAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MyViewHolder helper, LocalExpressionItem item) {
        SimpleDraweeView picSdv = helper.getView(R.id.pic_sdv);
        FrescoProxy.showSimpleView(picSdv, item.getPath());
    }

    public class MyViewHolder extends BaseViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }
}
