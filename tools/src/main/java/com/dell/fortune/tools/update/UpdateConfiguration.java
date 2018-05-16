/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.tools.update;

public class UpdateConfiguration {
    private String mVersionCodeStr;
    private String mVersionContent;
    private String mUrl;
    private boolean mIsForce;
    private String mDir;
    private int appLogoResourceId;

    public UpdateConfiguration(String mVersionCodeStr, String mVersionContent, String mUrl, boolean mIsForce, String mDir, int appLogoResourceId) {
        this.mVersionCodeStr = mVersionCodeStr;
        this.mVersionContent = mVersionContent;
        this.mUrl = mUrl;
        this.mIsForce = mIsForce;
        this.mDir = mDir;
        this.appLogoResourceId = appLogoResourceId;
    }

    public String getVersionCodeStr() {
        return mVersionCodeStr;
    }

    public void setVersionCodeStr(String versionCodeStr) {
        mVersionCodeStr = versionCodeStr;
    }

    public String getVersionContent() {
        return mVersionContent;
    }

    public void setVersionContent(String versionContent) {
        mVersionContent = versionContent;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public boolean isForce() {
        return mIsForce;
    }

    public void setForce(boolean force) {
        mIsForce = force;
    }

    public String getDir() {
        return mDir;
    }

    public void setDir(String dir) {
        mDir = dir;
    }

    public int getAppLogoResourceId() {
        return appLogoResourceId;
    }

    public void setAppLogoResourceId(int appLogoResourceId) {
        this.appLogoResourceId = appLogoResourceId;
    }
}
