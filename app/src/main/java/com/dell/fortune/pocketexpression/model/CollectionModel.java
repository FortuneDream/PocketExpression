package com.dell.fortune.pocketexpression.model;

import android.content.Context;

import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.callback.ToastUpdateListener;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.pocketexpression.common.BaseModel;
import com.dell.fortune.pocketexpression.config.FlagConstant;
import com.dell.fortune.pocketexpression.config.StrConstant;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.pocketexpression.model.callback.OnCheckCollectionListener;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionDaoOpe;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;
import com.dell.fortune.pocketexpression.util.common.RxApi;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;
import com.dell.fortune.pocketexpression.util.common.UserUtil;
import com.dell.fortune.tools.LogUtils;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import io.reactivex.functions.Consumer;

import static com.dell.fortune.pocketexpression.util.common.UserUtil.user;

/**
 * Created by 81256 on 2018/4/3.
 */

public class CollectionModel extends BaseModel<ExpressionItem> {

    private LocalExpressionDaoOpe localExpressionDaoOpe;

    public CollectionModel() {
        localExpressionDaoOpe = new LocalExpressionDaoOpe();
    }


    public void getLocalList(Consumer<List<LocalExpressionItem>> consumer) {
        RxApi.create(new Callable<List<LocalExpressionItem>>() {
            @Override
            public List<LocalExpressionItem> call() throws Exception {
                return localExpressionDaoOpe.findAll();
            }
        }).subscribe(consumer);
    }

    public void addCollection(Context context, List<ExpressionItem> expressionItems, ToastUpdateListener updateListener) {
        if (!checkCollection(context, expressionItems)) return;
        BmobRelation relation = new BmobRelation();
        for (ExpressionItem item : expressionItems) {
            relation.add(item);
        }
        user.setCollections(relation);//添加用户收藏
        user.update(updateListener);
    }

    //检验收藏
    private boolean checkCollection(Context context, List<ExpressionItem> expressionItems) {
        if (!UserUtil.checkLocalUser(true, (BaseActivity) context)) {
            ToastUtil.showToast(StrConstant.LOGIN_BEFORE);
            return false;
        }
        if (expressionItems == null || expressionItems.size() <= 0) {
            ToastUtil.showToast(StrConstant.EMPTY_PIC);
            return false;
        }
        return true;
    }

    //检验同步
    public void checkSyn(final OnCheckCollectionListener listener) {
        BmobQuery<ExpressionItem> query = new BmobQuery<>();
        query.addWhereRelatedTo("collections", new BmobPointer(UserUtil.user));
        query.findObjects(new ToastQueryListener<ExpressionItem>() {
            @Override
            public void onSuccess(List<ExpressionItem> list) {
                boolean isSynSuccess = true;
                List<ExpressionItem> unSaveItems = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    final ExpressionItem item = list.get(i);
                    //检验本地是否已经存在
                    LocalExpressionItem localExpressionItem = new LocalExpressionItem();
                    localExpressionItem.setMd5(item.getMd5());
                    if (!localExpressionDaoOpe.isExist(localExpressionItem)) {
                        unSaveItems.add(item);//还有
                        isSynSuccess = false;
                    }
                }
                LogUtils.e("是否有还未同步的表情包", String.valueOf(!isSynSuccess));
                listener.onCheckResult(isSynSuccess, unSaveItems);
            }

            @Override
            public void onFail(BmobException e) {
                super.onFail(e);
                listener.onCheckResult(true, new ArrayList<ExpressionItem>());//断网则不提示
            }
        });
    }

    //同步到本地
    public void synLocal() {
        checkSyn(new OnCheckCollectionListener() {
            @Override
            public void onCheckResult(boolean isSynSuccess, List<ExpressionItem> notSaveItems) {
                for (ExpressionItem item : notSaveItems) {
                    downloadItem(item);//下载同步到本地
                }
            }
        });
    }


    //下载同步到本地
    private void downloadItem(final ExpressionItem item) {
        String urlType = URLConnection.guessContentTypeFromName(item.getUrl());
        String type = urlType.replaceAll("image/", "");
        BmobFile file = new BmobFile(item.getMd5() + "." + type, "", item.getUrl());
        File targetDir = new File(FlagConstant.COLLECTION_DIR, file.getFilename());//目标文件
        file.download(targetDir, new DownloadFileListener() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtil.showToast("同步成功");
                    LocalExpressionItem localExpressionItem = new LocalExpressionItem();
                    localExpressionItem.setMd5(item.getMd5());
                    localExpressionItem.setPath(s);
                    localExpressionDaoOpe.insert(localExpressionItem);
                }
            }

            @Override
            public void onProgress(Integer integer, long l) {

            }
        });
    }


}
