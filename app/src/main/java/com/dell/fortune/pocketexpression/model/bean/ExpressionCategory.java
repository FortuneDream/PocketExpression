package com.dell.fortune.pocketexpression.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 81256 on 2018/3/26.
 */

public class ExpressionCategory extends BmobObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
