package com.dongua.simpleosc.base.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dongua.simpleosc.base.BasePresenter;
import com.dongua.simpleosc.base.BaseView;
import com.dongua.simpleosc.utils.UIUtil;
import com.orhanobut.logger.Logger;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.dongua.simpleosc.utils.Util.isMIUI;

/**
 * Created by duoyi on 17-11-15.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

//    protected BasePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());

        mUnbinder = ButterKnife.bind(this);

        setTranslucentStatus();

        initBundle(savedInstanceState);
//        initPresenter();
        initView();
        initData();
    }

    protected void initPresenter() {
//        mPresenter = getPresenter();
    }
//
//    protected BasePresenter getPresenter() {
//        return null;
//    }

    protected void initBundle(Bundle savedInstanceState) {

    }

    protected void saveBundle(Bundle outState) {

    }


    protected abstract int getLayoutID();

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveBundle(outState);
//        Logger.d("onSaveInstanceState");
    }

    protected void initView() {

    }


    protected void initData() {

    }

//    @Override
//    public void setPresenter(Object presenter) {
//
//    }


    private void setTranslucentStatus() {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        winParams.flags |= bits;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //字体反转到深色
            win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (isMIUI()) {
            MIUISetStatusBarLightMode(win, true);
        }
        win.setAttributes(winParams);
    }


    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

}
