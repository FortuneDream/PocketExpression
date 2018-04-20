package com.dell.fortune.pocketexpression.module.home.find;


import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.common.BasePresenter;
import com.dell.fortune.pocketexpression.common.IBaseView;
import com.dell.fortune.pocketexpression.model.FindModel;
import com.dell.fortune.pocketexpression.model.bean.ExpressionShare;

import java.util.List;

public class HomeFindPresenter extends BasePresenter<HomeFindPresenter.IView> {
    private int mPage;
    private FindModel findModel;

    public HomeFindPresenter(IView view) {
        super(view);
        findModel = new FindModel();
        mPage = -1;
    }

    public void getList() {
        mPage++;
        findModel.getList(mPage, new ToastQueryListener<ExpressionShare>() {
            @Override
            public void onSuccess(List<ExpressionShare> list) {
                mView.setList(list);
            }
        });
    }

    public void uploadExpression() {

    }

    interface IView extends IBaseView {

        void setList(List<ExpressionShare> list);
    }
}
