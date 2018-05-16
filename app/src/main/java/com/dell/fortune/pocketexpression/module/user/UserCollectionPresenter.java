package com.dell.fortune.pocketexpression.module.user;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.dell.fortune.pocketexpression.common.BasePresenter;
import com.dell.fortune.pocketexpression.common.IBaseView;
import com.dell.fortune.pocketexpression.model.CollectionModel;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;

import java.io.File;
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
        File file = new File(item.getPath());//这里share.jpg是sd卡根目录下的一个图片文件
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".fileProvider", file);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        }
        mContext.startActivity(Intent.createChooser(intent, "分享图片"));
    }

    public void synLocal() {
        collectionModel.synLocal();
    }

    interface IView extends IBaseView {

        void setList(List<LocalExpressionItem> list);
    }
}
