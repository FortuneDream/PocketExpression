package com.dell.fortune.pocketexpression.model;

import android.content.Context;
import android.os.Environment;

import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.callback.ToastUpdateListener;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.pocketexpression.common.BaseModel;
import com.dell.fortune.pocketexpression.common.BmobConstant;
import com.dell.fortune.pocketexpression.config.StrConstant;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.pocketexpression.model.bean.MyUser;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionDaoOpe;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItem;
import com.dell.fortune.pocketexpression.model.dao.LocalExpressionItemDao;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;
import com.dell.fortune.pocketexpression.util.common.UserUtil;

import java.io.File;
import java.net.URLConnection;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

import static com.dell.fortune.pocketexpression.util.common.UserUtil.user;

/**
 * Created by 81256 on 2018/4/3.
 */

public class CollectionModel extends BaseModel<ExpressionItem> {
    //本地图片位置
    private String mSaveDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "" + "ExpressionCollection" + File.separator;
    private LocalExpressionDaoOpe localExpressionDaoOpe;

    public CollectionModel() {
        localExpressionDaoOpe = new LocalExpressionDaoOpe();
    }

    public interface OnAddCollectionResult {
        void onResult();
    }

    @Override
    public void getList(int page, ToastQueryListener<ExpressionItem> listener) {
        super.getList(page, listener);
        BmobQuery<ExpressionItem> query = new BmobQuery<>();
        initDefaultListQuery(query, page);
        query.addWhereRelatedTo(BmobConstant.BMOB_COLLECTIONS, new BmobPointer(user));
        query.findObjects(listener);
    }

    public void addCollection(Context context, List<ExpressionItem> expressionItems, final OnAddCollectionResult onAddCollectionResult) {
        if (!checkCollection(context, expressionItems)) return;
        BmobRelation relation = new BmobRelation();
        for (ExpressionItem item : expressionItems) {
            relation.add(item);
        }
        user.setCollections(relation);//添加用户收藏
        user.update(new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast("成功收藏");
                onAddCollectionResult.onResult();
            }
        });
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

    //同步到本地
    public void synLocal() {
        final File dir = new File(mSaveDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        BmobQuery<ExpressionItem> query = new BmobQuery<>();
        query.addWhereRelatedTo("collections", new BmobPointer(UserUtil.user));
        query.findObjects(new ToastQueryListener<ExpressionItem>() {
            @Override
            public void onSuccess(List<ExpressionItem> list) {
                for (int i = 0; i < list.size(); i++) {
                    final ExpressionItem item = list.get(i);
                    //检验本地是否已经存在
                    LocalExpressionItem localExpressionItem = new LocalExpressionItem();
                    localExpressionItem.setMd5(item.getMd5());
                    if (localExpressionDaoOpe.isExist(localExpressionItem)) {
                        continue;
                    }
                    //下载
                    String urlType = URLConnection.guessContentTypeFromName(item.getUrl());
                    String type = urlType.replaceAll("image/", "");
                    BmobFile file = new BmobFile(item.getMd5() + "." + type, "", item.getUrl());
                    file.download(dir, new DownloadFileListener() {
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
        });
    }


}
