package com.dell.fortune.core.model.event;

/**
 * Created by 81256 on 2018/4/11.
 */

public class RefreshingViewEvent {
    private boolean isShow;

    public RefreshingViewEvent(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
