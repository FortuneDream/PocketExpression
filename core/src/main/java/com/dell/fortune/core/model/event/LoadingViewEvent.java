package com.dell.fortune.core.model.event;

/**
 * Created by 81256 on 2018/3/26.
 */

public class LoadingViewEvent {
    private boolean isShow;

    public LoadingViewEvent(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
