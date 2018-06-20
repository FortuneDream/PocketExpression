/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.core.common;


import com.dell.fortune.core.callback.ToastQueryListener;

import cn.bmob.v3.BmobQuery;


public class BaseModel<T> {
    public String DEFAULT_INVERTED_CREATE = BmobConstant.BMOB_CREATE_AT;//默认逆序
    public int DEFAULT_LIMIT = 10;

    public void initDefaultListQuery(BmobQuery<?> query) {
        query.order(DEFAULT_INVERTED_CREATE);
    }

    public void initDefaultListQuery(BmobQuery<?> query, int mPage) {
        initDefaultListQuery(query);
        query.setLimit(DEFAULT_LIMIT);
        query.setSkip(mPage * DEFAULT_LIMIT);
    }


    public void getList(int page, ToastQueryListener<T> listener) {
        //可选
    }

    public void getList(Object equalsObject, int page, ToastQueryListener<T> listener) {
        //可选
    }


}
