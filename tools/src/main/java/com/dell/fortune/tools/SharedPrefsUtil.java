/*
 * Copyright (c) 2018.
 * 版块归Github.FortuneDream 所有
 */

package com.dell.fortune.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 鹏君 on 2018/4/22.
 */
public class SharedPrefsUtil {
    private static SharedPreferences sp;
    private static String defaultFileName = "default_sp";

    private SharedPrefsUtil() {
    }

    public static void init(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(defaultFileName, Context.MODE_PRIVATE);
        }
    }

    public static SharedPreferences getInstance() {
        if (sp == null) {
            throw new ExceptionInInitializerError("SharedPreference尚未初始化，请在Application中进行初始化。");
        }
        return sp;
    }

    public static String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public static void putString(String key, String value) {
        if (value != null) {
            sp.edit().putString(key, value.trim()).apply();
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public static void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public static void putInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public static void putFloat(String key, float value) {
        sp.edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public static void putLong(String key, long value) {
        sp.edit().putLong(key, value).apply();
    }

    public static long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public static void remove(String key) {
        sp.edit().remove(key).apply();
    }
}
