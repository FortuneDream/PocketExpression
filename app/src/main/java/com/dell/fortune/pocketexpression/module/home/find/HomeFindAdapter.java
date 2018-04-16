package com.dell.fortune.pocketexpression.module.home.find;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.pocketexpression.model.bean.ExpressionShare;
import com.dell.fortune.pocketexpression.util.common.FrescoProxy;
import com.dell.fortune.pocketexpression.util.common.LogUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class HomeFindAdapter extends BaseQuickAdapter<ExpressionShare, HomeFindAdapter.MyViewHolder> {
    private SimpleDraweeView coverImgOne;
    private SimpleDraweeView coverImgTwo;
    private SimpleDraweeView coverImgThree;
    private SimpleDraweeView shareUserHead;

    public HomeFindAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MyViewHolder helper, ExpressionShare item) {
        shareUserHead=helper.getView(R.id.user_share_head);
        coverImgOne = helper.getView(R.id.share_cover_dv_1);
        coverImgTwo = helper.getView(R.id.share_cover_dv_2);
        coverImgThree = helper.getView(R.id.share_cover_dv_3);
        FrescoProxy.showSimpleView(coverImgOne, item.getCoverImgOne());
        FrescoProxy.showSimpleView(coverImgTwo, item.getCoverImgTwo());
        FrescoProxy.showSimpleView(coverImgThree, item.getCoverImgThree());
        FrescoProxy.showSimpleView(shareUserHead,item.getShareUser().getHeadUrl());
    }

    public class MyViewHolder extends BaseViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }
}
