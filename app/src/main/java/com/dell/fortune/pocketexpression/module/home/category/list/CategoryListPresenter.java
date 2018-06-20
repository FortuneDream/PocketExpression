/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home.category.list;


import com.dell.fortune.core.callback.ToastQueryListener;
import com.dell.fortune.core.callback.ToastUpdateListener;
import com.dell.fortune.core.common.BasePresenter;
import com.dell.fortune.core.common.IBaseView;
import com.dell.fortune.core.config.StrConstant;
import com.dell.fortune.pocketexpression.model.CollectionModel;
import com.dell.fortune.pocketexpression.model.ItemModel;
import com.dell.fortune.pocketexpression.model.ShareModel;
import com.dell.fortune.pocketexpression.model.bean.ExpressionCategory;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.tools.toast.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoryListPresenter extends BasePresenter<CategoryListPresenter.IView> {
    private int mPage;
    private ItemModel itemModel;
    private CollectionModel collectionModel;
    private ShareModel shareModel;

    public CategoryListPresenter(IView view) {
        super(view);
        itemModel = new ItemModel();
        collectionModel = new CollectionModel();
        shareModel = new ShareModel();
        mPage = -1;
    }

    public void getList(ExpressionCategory category) {
        mPage++;
        itemModel.getCategoryList(category, mPage, new ToastQueryListener<ExpressionItem>() {
            @Override
            public void onSuccess(List<ExpressionItem> list) {
                mView.setList(list);
            }
        });

    }

    public void collectionItem(ExpressionItem item) {
        mView.showLoading(true);
        List<ExpressionItem> items = new ArrayList<>();
        items.add(item);
        collectionModel.addCollection(mContext, items, new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                mView.showLoading(false);
                ToastUtil.showToast(StrConstant.COLLECTION_SUCCESS);
            }
        });
    }

    public void collectionAllItem(List<ExpressionItem> data) {
        collectionModel.addCollection(mContext, data, new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast(StrConstant.COLLECTION_SUCCESS);
            }
        });
    }

    //分享
    public void shareItem(final ExpressionItem item) {
        mView.showLoading(true);
        shareModel.shareNetPic(mContext, item);
    }


    interface IView extends IBaseView {

        void setList(List<ExpressionItem> list);
    }
}
