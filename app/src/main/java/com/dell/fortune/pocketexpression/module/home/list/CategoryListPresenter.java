/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.pocketexpression.module.home.list;


import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.common.BasePresenter;
import com.dell.fortune.pocketexpression.common.IBaseView;
import com.dell.fortune.pocketexpression.config.StrConstant;
import com.dell.fortune.pocketexpression.model.CollectionModel;
import com.dell.fortune.pocketexpression.model.ItemModel;
import com.dell.fortune.pocketexpression.model.bean.ExpressionCategory;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoryListPresenter extends BasePresenter<CategoryListPresenter.IView> {
    private int mPage;
    private ItemModel itemModel;
    private CollectionModel collectionModel;

    public CategoryListPresenter(IView view) {
        super(view);
        itemModel = new ItemModel();
        collectionModel = new CollectionModel();
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
        List<ExpressionItem> items = new ArrayList<>();
        items.add(item);
        collectionModel.addCollection(mContext, items, new CollectionModel.OnAddCollectionResult() {
            @Override
            public void onResult() {
                ToastUtil.showToast(StrConstant.COLLECTION_SUCCESS);
            }
        });
    }

    public void collectionAllItem(List<ExpressionItem> data) {
        collectionModel.addCollection(mContext, data, new CollectionModel.OnAddCollectionResult() {
            @Override
            public void onResult() {
                ToastUtil.showToast(StrConstant.COLLECTION_SUCCESS);
            }
        });
    }

    interface IView extends IBaseView {

        void setList(List<ExpressionItem> list);
    }
}
