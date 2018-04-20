package com.dell.fortune.pocketexpression.model.dao;

import com.dell.fortune.pocketexpression.model.bean.ExpressionItem;
import com.dell.fortune.pocketexpression.util.common.GreenDaoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 81256 on 2018/4/19.
 */

public class LocalExpressionDaoOpe {

    public void insert(LocalExpressionItem item) {
        List<LocalExpressionItem> list = new ArrayList<>();
        for (LocalExpressionItem existItem : list) {
            if (existItem.getMd5().equals(item.getMd5())) {
                return;//查重
            }
        }
        GreenDaoUtil.getSession().getLocalExpressionItemDao().insert(item);
    }

    public void delete(LocalExpressionItem item) {
        GreenDaoUtil.getSession().getLocalExpressionItemDao().delete(item);
    }

    public List<LocalExpressionItem> findAll() {
       return GreenDaoUtil.getSession().getLocalExpressionItemDao().loadAll();
    }

    public boolean isExist(LocalExpressionItem item){
        return GreenDaoUtil.getSession().getLocalExpressionItemDao().queryBuilder()
                .where(LocalExpressionItemDao.Properties.Md5.eq(item.getMd5()))
                .list()
                .size()>0;
}
}
