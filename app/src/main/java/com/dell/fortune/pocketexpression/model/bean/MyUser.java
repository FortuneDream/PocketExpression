package com.dell.fortune.pocketexpression.model.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 81256 on 2018/3/28.
 */

public class MyUser extends BmobUser {
    //昵称
    private String nickName;
    //拥有的表情包数量
    private BmobRelation expressionItems;
    //头像
    private String headUrl;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BmobRelation getExpressionItems() {
        return expressionItems;
    }

    public void setExpressionItems(BmobRelation expressionItems) {
        this.expressionItems = expressionItems;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
