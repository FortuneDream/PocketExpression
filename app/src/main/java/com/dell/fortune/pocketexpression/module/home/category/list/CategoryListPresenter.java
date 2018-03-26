package com.dell.fortune.pocketexpression.module.home.category.list;


import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.common.BasePresenter;
import com.dell.fortune.pocketexpression.common.IBaseView;
import com.dell.fortune.pocketexpression.model.ItemModel;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;

import java.util.List;

public class CategoryListPresenter extends BasePresenter<CategoryListPresenter.IView> {
    private int mPage;
    private ItemModel itemModel;

    public CategoryListPresenter(IView view) {
        super(view);
        itemModel=new ItemModel();
    }

    public void getList(final boolean isRefreshing) {
        mPage++;
        if (isRefreshing){
            mPage=0;
        }
        itemModel.getList(mPage, new ToastQueryListener<ExpressionItem>() {
            @Override
            public void onSuccess(List<ExpressionItem> list) {
                mView.setList(isRefreshing,list);
            }
        });

    }

    interface IView extends IBaseView {

        void setList(boolean isRefreshing, List<ExpressionItem> list);
    }
}
