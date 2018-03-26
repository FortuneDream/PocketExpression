package com.dell.fortune.pocketexpression.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 81256 on 2018/3/26.
 */

public class ExpressionItem extends BmobObject {
    //用ObjectId当做Name
    private String url;
    private ExpressionCategory category;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ExpressionCategory getCategory() {
        return category;
    }

    public void setCategory(ExpressionCategory category) {
        this.category = category;
    }
}
