package com.dell.fortune.pocketexpression.model.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 81256 on 2018/4/16.
 */

public class ExpressionShare extends BmobObject {
    //分享的多个图片
    private BmobRelation shares;
    //属于某个用户
    private MyUser shareUser;
    //封面图片一
    private String coverImgOne;
    //封面图片二
    private String coverImgTwo;
    //封面图片三
    private String coverImgThree;

    public BmobRelation getShares() {
        return shares;
    }

    public void setShares(BmobRelation shares) {
        this.shares = shares;
    }

    public MyUser getShareUser() {
        return shareUser;
    }

    public void setShareUser(MyUser shareUser) {
        this.shareUser = shareUser;
    }

    public String getCoverImgOne() {
        return coverImgOne;
    }

    public void setCoverImgOne(String coverImgOne) {
        this.coverImgOne = coverImgOne;
    }

    public String getCoverImgTwo() {
        return coverImgTwo;
    }

    public void setCoverImgTwo(String coverImgTwo) {
        this.coverImgTwo = coverImgTwo;
    }

    public String getCoverImgThree() {
        return coverImgThree;
    }

    public void setCoverImgThree(String coverImgThree) {
        this.coverImgThree = coverImgThree;
    }
}
