package com.dell.fortune.pocketexpression.util.common;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 81256 on 2018/4/10.
 */

public class FrescoProxy {

    public static void showNetSimpleView(SimpleDraweeView draweeView, String uri) {
        if (uri != null) {
            draweeView.setImageURI(uri);
        }
    }

    public static void showLocalSimpleView(SimpleDraweeView draweeView, String uri) {
        if (uri != null) {
            draweeView.setImageURI("file://" + uri);
        }
    }

}
