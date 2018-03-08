package com.dongua.simpleosc.utils;

/**
 * Created by duoyi on 17-11-24.
 */


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ActivitySwitcher {


    public static void switchTo(Activity activity,
                                Class<? extends Activity> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        activity.finish();
    }
    public static void switchTo(Activity activity,
                                Class<? extends Activity> cls, Bundle bundle ) {
        switchTo(activity,cls,bundle,false);
    }
    public static void switchTo(Activity activity,
                                Class<? extends Activity> cls, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        if (isFinish)
            activity.finish();
    }

    @SuppressWarnings("unchecked")
    public static void switchTo(Activity activity
            , Class<? extends Activity> cls
            , HashMap<String, Object> hashMap) {
        Intent intent = new Intent(activity, cls);
        for (Object o : hashMap.entrySet()) {
            Entry<String, Object> entry = (Entry<String, Object>) o;
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                intent.putExtra(key, (String) value);
            }
            if (value instanceof Boolean) {
                intent.putExtra(key, (boolean) value);
            }
            if (value instanceof Integer) {
                intent.putExtra(key, (int) value);
            }
            if (value instanceof Float) {
                intent.putExtra(key, (float) value);
            }
            if (value instanceof Double) {
                intent.putExtra(key, (double) value);
            }
        }
        activity.startActivity(intent);
    }
}
