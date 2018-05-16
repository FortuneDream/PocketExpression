package com.dell.fortune.tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.dell.fortune.tools.toast.ToastUtil;

import java.io.File;
import java.net.URLEncoder;

/**
 * Created by 鹏君 on 2017/7/3.
 * （￣m￣）
 */

public class IntentUtil {

    //获得AppStoreIntent
    private static Intent getAppStoreIntent(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//加个栈
        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
        return intent;
    }

    //进入AppStore
    public static void enterAppStore(Context context) {
        Intent intent = getAppStoreIntent(context);
        if (intent.resolveActivity(context.getPackageManager()) != null) { //可以接收
            context.startActivity(intent);
        } else {
            ToastUtil.showToast("没有找到应用市场~");
        }
    }

    private static Intent getShareTextIntent(String content) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return intent;
    }

    //分享Text
    public static void shareText(Context context, String content) {
        Intent intent = getShareTextIntent(content);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtil.showToast("你的手机不支持分享~");
        }
    }

    //分享图片
    public static void sharePic(String path,Context context){
        File file = new File(path);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileProvider", file);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        }
        context.startActivity(Intent.createChooser(intent, "分享图片"));
    }

    //安装APk,manifest的provider.authorities=包名.fileProvider
//    总而言之，就是Android不再允许在app中把file://Uri暴露给其他app，包括但不局限于通过Intent或ClipData 等方法。
//
//    原因在于使用file://Uri会有一些风险，比如：
//
//    文件是私有的，接收file://Uri的app无法访问该文件。
//    在Android6.0之后引入运行时权限，如果接收file://Uri的app没有申请READ_EXTERNAL_STORAGE权限，在读取文件时会引发崩溃。
//    因此，google提供了FileProvider，使用它可以生成content://Uri来替代file://Uri。
    public static void installApk(Context context, File apkFile) {
        //Android N以上需要特殊处理
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri installUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileProvider", apkFile);
            intent.setDataAndType(installUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    public static void enterAlipay(Context context) {
        String url = "HTTPS://QR.ALIPAY.COM/FKX07871CN2F2NE98FNBDF";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(context.getPackageManager()) != null) { //可以接收
            context.startActivity(intent);
        } else {
            ToastUtil.showToast("你的手机没有安装支付宝");
        }
    }

    public static void openAliPay2Pay(Context context) {
        String qrcode = "HTTPS://QR.ALIPAY.COM/FKX07871CN2F2NE98FNBDF";
        if (!openAlipayPayPage(context, qrcode)) {
            ToastUtil.showToast("调起支付宝失败");
        }
    }

    private static boolean openAlipayPayPage(Context context, String qrcode) {
        try {
            qrcode = URLEncoder.encode(qrcode, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            final String alipayqr = "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + qrcode;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(alipayqr + "%3F_s%3Dweb-other&_t=" + System.currentTimeMillis()));
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
