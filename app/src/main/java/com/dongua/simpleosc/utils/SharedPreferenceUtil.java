package com.dongua.simpleosc.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dongua.simpleosc.AppManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by duoyi on 17-11-24.
 */

public class SharedPreferenceUtil {
    public static final String PREF_COOKIE = "cookies";
    public static final String ACCESS_TOKEN = "a_token";
    public static final String REFRESH_TOKEN = "r_token";

    public static void putSharedPreferences(String key, Object value) {
        Context context = AppManager.getInstance().getAppContext();
        String type = value.getClass().getSimpleName();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) value);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) value);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) value);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) value);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) value);
        } else if ("Set".equals(type)){
            editor.putStringSet(key, (Set)value);
        } else if ("HashSet".equals(type)){
            editor.putStringSet(key, (HashSet)value);
        }
        editor.commit();
    }
    public static Object getSharedPreferences(String key, Object defValue) {
        Context context = AppManager.getInstance().getAppContext();
        String type = defValue.getClass().getSimpleName();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        //defValue为为默认值，如果当前获取不到数据就返回它
        if ("Integer".equals(type)) {
            return sharedPreferences.getInt(key, (Integer) defValue);
        } else if ("Boolean".equals(type)) {
            return sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if ("String".equals(type)) {
            return sharedPreferences.getString(key, (String) defValue);
        } else if ("Float".equals(type)) {
            return sharedPreferences.getFloat(key, (Float) defValue);
        } else if ("Long".equals(type)) {
            return sharedPreferences.getLong(key, (Long) defValue);
        }else if ("Set".equals(type)){
            return sharedPreferences.getStringSet(key, (Set) defValue);
        } else if ("HashSet".equals(type)){
            return sharedPreferences.getStringSet(key, (Set) defValue);
        }
        return null;
    }
}