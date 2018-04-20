package com.dell.fortune.pocketexpression.module.user;


import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.common.BasePresenter;
import com.dell.fortune.pocketexpression.common.IBaseView;
import com.dell.fortune.pocketexpression.model.CollectionModel;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;

import java.io.File;
import java.security.MessageDigest;
import java.util.List;

public class UserCollectionPresenter extends BasePresenter<UserCollectionPresenter.IView> {
    private CollectionModel collectionModel;
    private int mPage;

    public UserCollectionPresenter(IView view) {
        super(view);
        collectionModel = new CollectionModel();
        mPage = -1;
    }

    public void getList() {
        mPage++;
        collectionModel.getList(mPage, new ToastQueryListener<ExpressionItem>() {
            @Override
            public void onSuccess(List<ExpressionItem> list) {
                mView.setList(list);
            }
        });
    }

    //只能分享本地
    public void shareImage(ExpressionItem item) {
        String path = Environment.getExternalStorageDirectory() + File.separator + "";//sd根目录
        File file = new File(path, "share" + ".jpg");//这里share.jpg是sd卡根目录下的一个图片文件
        Uri imageUri = Uri.parse(item.getUrl());
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        mContext.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }

    public void synLocal() {
        collectionModel.synLocal();
    }

    interface IView extends IBaseView {

        void setList(List<ExpressionItem> list);
    }
}
