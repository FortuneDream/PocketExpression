package com.dell.fortune.pocketexpression.model;

import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.common.BaseModel;
import com.dell.fortune.pocketexpression.common.BmobConstant;
import com.dell.fortune.pocketexpression.model.bean.ExpressionCategory;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 81256 on 2018/3/26.
 */

public class ItemModel extends BaseModel<ExpressionItem> {
    private int LIMIT_NUMBER = 9;//每次加载9个;

    @Override
    public void initDefaultListQuery(BmobQuery<?> query, int mPage) {
        super.initDefaultListQuery(query);
        query.setLimit(LIMIT_NUMBER);
        query.setSkip(mPage * LIMIT_NUMBER);
    }

    //所有的图片
    @Override
    public void getList(int page, ToastQueryListener<ExpressionItem> listener) {
        super.getList(page, listener);
        BmobQuery<ExpressionItem> query = new BmobQuery<>();
        initDefaultListQuery(query, page);
        query.findObjects(listener);
    }



    //某种种类的图片列表
    public void getCategoryList(ExpressionCategory category, int page, ToastQueryListener<ExpressionItem> listener) {
        BmobQuery<ExpressionItem> query = new BmobQuery<>();
        initDefaultListQuery(query, page);
        query.setLimit(9);//九宫格
        query.addWhereEqualTo(BmobConstant.CATEGORY, new BmobPointer(category));
        query.findObjects(listener);
    }
}
