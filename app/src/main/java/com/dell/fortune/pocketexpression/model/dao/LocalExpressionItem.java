package com.dell.fortune.pocketexpression.model.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import cn.bmob.v3.BmobObject;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 81256 on 2018/3/28.
 */
@Entity
public class LocalExpressionItem extends BmobObject {
    @Id
    private Long id;
    @Property
    private String path;
    @Generated(hash = 801340885)
    public LocalExpressionItem(Long id, String path) {
        this.id = id;
        this.path = path;
    }
    @Generated(hash = 994931195)
    public LocalExpressionItem() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
