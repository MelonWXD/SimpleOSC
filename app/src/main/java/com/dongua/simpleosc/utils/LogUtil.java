package com.dongua.simpleosc.utils;

import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

/**
 * Created by Leiws on 17-11-18.
 */

public class LogUtil {
    public static void d(@Nullable Object o) {
        if (o == null)
            Logger.d("");
        else
            Logger.d(o);
    }
}
