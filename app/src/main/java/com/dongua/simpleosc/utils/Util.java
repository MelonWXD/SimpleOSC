package com.dongua.simpleosc.utils;

import android.os.Build;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * Created by duoyi on 17-11-25.
 */

public class Util {

    private static Gson gson = new Gson();

    public static JsonObject string2Json(String jsonstr) {
        JsonObject jsonData = new JsonParser().parse(jsonstr).getAsJsonObject();
        return jsonData;
    }

//    public static JsonArray string2JsonArray(String jsonstr) {
//        JsonArray jsonArray = new JsonParser().parse(jsonstr).getAsJsonArray();
//        return jsonArray;
//    }


    public static String bean2Json(Object bean) {
        return new Gson().toJson(bean);
    }

    public static Object json2Bean(String json, Class beanClass) {

        Object res = gson.fromJson(json, beanClass);
        return res;
    }

    public static JsonArray beanList2JsonArray(List<Object> beanList) {
        JsonArray jsonArray = gson.toJsonTree(beanList).getAsJsonArray();
        return jsonArray;
    }


    public static List jsonArray2BeanList(String jsonarray, Type type) {
//        type = new TypeToken<beanClass>().getType()
        return gson.fromJson(jsonarray, type);

    }

    public static String jsonArray2String(JsonArray jsonarray) {
//        type = new TypeToken<beanClass>().getType()
        return gson.toJson(jsonarray);
    }

    /**
     * return true if d1 is later d2
     */
//    /*2017-12-09 08:24:49*/
    public static boolean dateCompare(String d1, String d2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);
            return date1.getTime() > date2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * return true if d1 is later than now + plusSecond
     */
    public static boolean dateCompareNow(String d1, int plusSecond) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = sdf.parse(d1);
            Date now = new Date();
            return date1.getTime() > now.getTime() + plusSecond;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String dateFormat(String d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String ret = "";
        try {
            long pubDate = sdf.parse(d).getTime();
            long now = new Date().getTime();
//            com.orhanobut.logger.Logger.d(now-pubDate);
            float time = (now - pubDate) / (24 * 60 * 60 * 1000F);
            if (time < 1.0 / 24) {
                ret = String.format(Locale.getDefault(), "%d分钟前", (int) (time * 24 * 60));
            } else if (time < 1) {
                ret = String.format(Locale.getDefault(), "%d小时前", (int) (time * 24));
            } else if (time < 2) {
                ret = "昨天";
            } else if (time < 3) {
                ret = "前天";
            } else {
                ret = String.format(Locale.getDefault(), "%d天前", (int) time);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return ret;
    }

    public static long str2Date(String d) {
//        Logger.d("str2Date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return sdf.parse(d).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }


    public static String formatTweetContent(String src){
//        String regex = "(?<=<li>)[\\\\s\\\\S]+?(?=</li>)";
        String regex = "<[^>]*>";

        return src.replaceAll(regex,"");
    }

    //检测MIUI
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUI() {
        String device = Build.MANUFACTURER;

        if (device.equals("Xiaomi")) {
            Properties prop = new Properties();
            try {
                prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } else {
            return false;
        }
    }
}
