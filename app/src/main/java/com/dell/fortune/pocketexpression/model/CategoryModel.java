package com.dell.fortune.pocketexpression.model;

import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.common.BaseModel;
import com.dell.fortune.pocketexpression.model.bean.ExpressionCategory;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 81256 on 2018/3/26.
 */

public class CategoryModel extends BaseModel<ExpressionCategory> {

    @Override
    public void getList(int page, ToastQueryListener<ExpressionCategory> listener) {
        super.getList(page, listener);
        BmobQuery<ExpressionCategory> query = new BmobQuery<>();
        initDefaultListQuery(query, page);
        query.findObjects(listener);
    }
}
