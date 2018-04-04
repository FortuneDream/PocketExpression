package com.dell.fortune.pocketexpression.util.common.gif;

public abstract interface GifAction {

    /**
     * gif 接口
     * @param parseStatus 解析成功true
     * @param frameIndex -1时说明已经全部解析完成，当大于0时为解析得到的gif帧编号
     */
    public abstract void parseOk(boolean parseStatus,int frameIndex);
}