/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionStatusUtil {
    public static boolean checkWifeStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfoActivity = connectivityManager.getActiveNetworkInfo();
        if (mobNetInfoActivity == null) {
            return false;
        }
        int netType = mobNetInfoActivity.getType();
        if (netType == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;

    }
}
