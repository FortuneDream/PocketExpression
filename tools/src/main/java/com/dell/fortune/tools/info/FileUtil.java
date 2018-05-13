package com.dell.fortune.tools.info;

import android.content.Context;
import android.support.annotation.Nullable;

import com.dell.fortune.tools.toast.Toasts;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    private static Context sContext;

    //必须先调用
    public static void init(Context context) {
        sContext = context;
    }

    //获得内部存储路径
    public static String getDirPath(){
        return sContext.getFilesDir().getAbsolutePath();
    }

    //根据url后缀名创建文件,返回String类型的本地地址,可能为Null
    @Nullable
    public static File createFileByNet(String url, String dirPath) {
        boolean isSucceed = true;
        if (!dirPath.endsWith("/")) {
            dirPath = dirPath + "/";
        }
        File path = new File(dirPath);
        if (!path.exists()) {
            isSucceed = path.mkdirs();
            if (!isSucceed) {
                return null;
            }
        }
        String[] s = url.split("/");//123.apk
        String filePathString = dirPath + s[s.length - 1];
        File file = new File(filePathString);
        if (!file.exists()) {
            try {
                isSucceed = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (isSucceed) {
            return file;
        } else {
            return null;
        }
    }


}
