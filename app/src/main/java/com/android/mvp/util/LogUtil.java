package com.android.mvp.util;

import android.util.Log;

public class LogUtil {

    private static boolean sEnable = false;

    public static void enable() {
        sEnable = true;
    }

    public static void disable() {
        sEnable = false;
    }

    public static void i(String tag, String msg) {
        if (sEnable) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (sEnable) {
            Log.i(tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (sEnable) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (sEnable) {
            Log.e(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (sEnable) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (sEnable) {
            Log.d(tag, msg, tr);
        }
    }
}
