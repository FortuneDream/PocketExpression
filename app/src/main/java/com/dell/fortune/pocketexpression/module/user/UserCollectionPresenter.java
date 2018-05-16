package com.dell.fortune.pocketexpression.module.user;


import com.dell.fortune.pocketexpression.common.BasePresenter;
import com.dell.fortune.pocketexpression.common.IBaseView;
import com.dell.fortune.pocketexpression.model.CollectionModel;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;
import com.dell.fortune.tools.IntentUtil;

import java.util.List;

import io.reactivex.functions.Consumer;

public class UserCollectionPresenter extends BasePresenter<UserCollectionPresenter.IView> {
    private CollectionModel collectionModel;

    public UserCollectionPresenter(IView view) {
        super(view);
        collectionModel = new CollectionModel();
    }

    public void getList() {
        collectionModel.getLocalList(new Consumer<List<LocalExpressionItem>>() {
            @Override
            public void accept(List<LocalExpressionItem> localExpressionItems) throws Exception {
                mView.setList(localExpressionItems);
            }
        });
    }

    //只能分享本地
    public void shareImage(LocalExpressionItem item) {
        IntentUtil.sharePic(item.getPath(), mContext);
    }

    public void synLocal() {
        collectionModel.synLocal();
    }

    interface IView extends IBaseView {

        void setList(List<LocalExpressionItem> list);
    }
}
