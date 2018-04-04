package com.dell.fortune.pocketexpression.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 81256 on 2018/3/26.
 */

public class ExpressionCategory extends BmobObject {
    private String name;
    //封面图片一
    private String coverImgOne;
    //封面图片二
    private String coverImgTwo;
    //封面图片三
    private String coverImgThree;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverImgOne() {
        return coverImgOne;
    }

    public void setCoverImgOne(String converImgOne) {
        this.coverImgOne = converImgOne;
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
