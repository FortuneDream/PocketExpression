package com.dell.fortune.pocketexpression.model;

import android.content.Context;

import com.dell.fortune.pocketexpression.callback.ToastQueryListener;
import com.dell.fortune.pocketexpression.callback.ToastUpdateListener;
import com.dell.fortune.pocketexpression.common.BaseActivity;
import com.dell.fortune.pocketexpression.common.BaseModel;
import com.dell.fortune.pocketexpression.common.BmobConstant;
import com.dell.fortune.pocketexpression.config.StrConstant;
import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;
import com.dell.fortune.pocketexpression.util.common.UserUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

import static com.dell.fortune.pocketexpression.util.common.UserUtil.user;

/**
 * Created by 81256 on 2018/4/3.
 */

public class CollectionModel extends BaseModel<ExpressionItem> {
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
        for (ExpressionItem item:expressionItems){
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
}
