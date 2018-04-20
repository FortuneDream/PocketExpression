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
    @Property
    private String md5;
    @Generated(hash = 92202490)
    public LocalExpressionItem(Long id, String path, String md5) {
        this.id = id;
        this.path = path;
        this.md5 = md5;
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
    public String getMd5() {
        return this.md5;
    }
    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
