package com.dell.fortune.pocketexpression.model;

import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.common.BaseModel;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 81256 on 2018/3/26.
 */

public class ItemModel extends BaseModel<ExpressionItem> {
    @Override
    public void getList(int page, ToastQueryListener<ExpressionItem> listener) {
        super.getList(page, listener);
        BmobQuery<ExpressionItem> query = new BmobQuery<>();
        initDefaultListQuery(query, page);
        query.findObjects(listener);
    }
}
