package com.dell.fortune.pocketexpression.module.suspend;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dell.fortune.pocketexpression.R;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;
import com.dell.fortune.pocketexpression.util.common.FrescoProxy;
import com.dell.fortune.tools.LogUtils;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 81256 on 2018/3/28.
 */

public class SuspendContentAdapter extends BaseQuickAdapter<LocalExpressionItem, BaseViewHolder> {

    public SuspendContentAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalExpressionItem item) {
        SimpleDraweeView img = helper.getView(R.id.item_image_sdv);
        FrescoProxy.showLocalSimpleView(img, item.getPath());
        LogUtils.e(item.getPath());
    }
}
