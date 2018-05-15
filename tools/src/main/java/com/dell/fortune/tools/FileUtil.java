package com.dell.fortune.tools;

import android.content.Context;
import android.support.annotation.Nullable;

import com.dell.fortune.tools.toast.Toasts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    private static Context sContext;

    //必须先调用
    public static void init(Context context) {
        sContext = context;
    }

    //获得内部存储路径
    public static String getDirPath() {
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

    public static void copyFile(String oldPath, String newPath) {
        if (oldPath != null) {
            File s = new File(oldPath);
            if (s.exists()) {
                s.renameTo(new File(newPath));
            }
        }
    }

    //深搜删除
    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }


}
