package com.dell.fortune.pocketexpression.module.home.category;

import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.model.bean.ExpressionCategory;
import com.dell.fortune.pocketexpression.util.common.FrescoProxy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 81256 on 2018/3/26.
 */

public class HomeCategoryAdapter extends BaseQuickAdapter<ExpressionCategory, BaseViewHolder> {
    private SimpleDraweeView coverImgOne;
    private SimpleDraweeView coverImgTwo;
    private SimpleDraweeView coverImgThree;

    public HomeCategoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpressionCategory item) {
        helper.setText(R.id.category_name_tv, item.getName());
        coverImgOne = helper.getView(R.id.cover_dv_1);
        coverImgTwo = helper.getView(R.id.cover_dv_2);
        coverImgThree = helper.getView(R.id.cover_dv_3);
        FrescoProxy.showSimpleView(coverImgOne, item.getCoverImgOne());
        FrescoProxy.showSimpleView(coverImgTwo, item.getCoverImgTwo());
        FrescoProxy.showSimpleView(coverImgThree, item.getCoverImgThree());
    }

    class MyViewHolder extends BaseViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }
}
