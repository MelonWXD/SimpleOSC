package com.dongua.simpleosc.utils;

import android.content.Context;
import android.widget.Toast;

import com.dongua.simpleosc.AppManager;

/**
 * Created by duoyi on 17-11-25.
 */

public class UIUtil {
    public static void showShortToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    public static void showShortToast(String msg) {
        Toast.makeText(AppManager.getInstance().getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    public static void showLongToast(String msg) {
        Toast.makeText(AppManager.getInstance().getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
