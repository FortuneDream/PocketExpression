package com.dell.fortune.pocketexpression.model;

import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.common.BaseModel;
import com.dell.fortune.pocketexpression.model.bean.ExpressionShare;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 81256 on 2018/4/16.
 */

public class FindModel extends BaseModel<ExpressionShare> {
    @Override
    public void getList(int page, ToastQueryListener<ExpressionShare> listener) {
        super.getList(page, listener);
        BmobQuery<ExpressionShare> query=new BmobQuery<>();
        initDefaultListQuery(query,page);
        query.include("shareUser");
        query.findObjects(listener);
    }
}
