package com.dell.fortune.pocketexpression.util.common;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 81256 on 2018/4/10.
 */

public class FrescoProxy {

    public static void showSimpleView(SimpleDraweeView draweeView, String uri) {
        if (uri != null) {
            draweeView.setImageURI(uri);
        }
    }
}
