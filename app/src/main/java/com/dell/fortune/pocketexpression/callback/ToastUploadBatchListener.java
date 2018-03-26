package com.dell.fortune.pocketexpression.callback;

import com.dell.fortune.pocketexpression.model.event.LoadingViewEvent;
import com.dell.fortune.pocketexpression.util.common.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by 鹏君 on 2017/1/31.
 */

public class ToastUploadBatchListener implements UploadBatchListener {


    public ToastUploadBatchListener() {
    }

    @Override
    public void onSuccess(List<BmobFile> list, List<String> list1) {

    }

    @Override
    public void onProgress(int i, int i1, int i2, int i3) {

    }

    @Override
    public void onError(int i, String s) {
        EventBus.getDefault().post(new LoadingViewEvent(false));
        ToastUtil.showToast("第" + i + "张图片上传错误:" + s);
    }


}
