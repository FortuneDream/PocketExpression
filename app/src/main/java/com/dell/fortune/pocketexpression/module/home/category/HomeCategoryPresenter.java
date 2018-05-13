package com.dell.fortune.pocketexpression.module.home.category;


import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.common.BasePresenter;
import com.dell.fortune.pocketexpression.common.IBaseView;
import com.dell.fortune.pocketexpression.config.FlagConstant;
import com.dell.fortune.pocketexpression.model.CategoryModel;
import com.dell.fortune.pocketexpression.model.bean.ExpressionCategory;
import com.dell.fortune.pocketexpression.module.home.category.list.CategoryListActivity;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;
import com.dell.fortune.tools.info.LogUtils;
import com.dell.fortune.tools.info.SharedPrefsUtil;

import java.util.List;

/**
 * Created by 81256 on 2018/3/18.
 */

public class HomeCategoryPresenter extends BasePresenter<HomeCategoryPresenter.IView> {
    private CategoryModel categoryModel;
    private int mPage;


    public HomeCategoryPresenter(IView view) {
        super(view);
        categoryModel = new CategoryModel();
        mPage = SharedPrefsUtil.getInt(FlagConstant.SP_CURRENT_PAGE, -1);
    }

    public void setPage(int page) {
        this.mPage = page;
        SharedPrefsUtil.putInt(FlagConstant.SP_CURRENT_PAGE,page);
    }

    public void getRefreshList() {
        mPage++;
        LogUtils.e("当前页数:" + mPage);
        categoryModel.getList(mPage, new ToastQueryListener<ExpressionCategory>() {
            @Override
            public void onSuccess(List<ExpressionCategory> list) {
                mView.setList(list);
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

        void setList(List<ExpressionCategory> list);
    }
}
