package com.dell.fortune.pocketexpression.module.home.category;


import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.common.BasePresenter;
import com.dell.fortune.pocketexpression.common.IBaseView;
import com.dell.fortune.pocketexpression.model.CategoryModel;
import com.dell.fortune.pocketexpression.model.bean.ExpressionCategory;
import com.dell.fortune.pocketexpression.module.home.category.list.CategoryListActivity;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;

import java.util.List;

/**
 * Created by 81256 on 2018/3/18.
 */

public class HomeCategoryPresenter extends BasePresenter<HomeCategoryPresenter.IView> {
    private CategoryModel categoryModel;
    private int page;

    public HomeCategoryPresenter(IView view) {
        super(view);
        categoryModel = new CategoryModel();
    }

    public void getList(final boolean isRefreshing) {
        page++;
        if (isRefreshing) {
            page = 0;
        }
        categoryModel.getList(page, new ToastQueryListener<ExpressionCategory>() {
            @Override
            public void onSuccess(List<ExpressionCategory> list) {
                mView.setList(isRefreshing, list);
            }
        });
    }

    public void enterCategoryListActivity(Object item) {
        if (item instanceof ExpressionCategory) {
            mContext.startActivity(CategoryListActivity.buildIntent(mContext, (ExpressionCategory) item));
        } else {
            ToastUtil.showToast("参数错误");
        }
    }


    public interface IView extends IBaseView {

        void setList(boolean isRefreshing, List<ExpressionCategory> list);
    }
}
