package com.dongua.simpleosc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.utils.ActivitySwitcher;
import com.dongua.simpleosc.utils.SharedPreferenceUtil;

import butterknife.BindView;

import static com.dongua.simpleosc.utils.SharedPreferenceUtil.ACCESS_TOKEN;
import static com.dongua.simpleosc.utils.SharedPreferenceUtil.PREF_COOKIE;

/**
 * Created by duoyi on 17-11-24.
 */

public class LaunchActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    protected void initData() {
        super.initData();


        String accessToken = (String) SharedPreferenceUtil.getSharedPreferences(ACCESS_TOKEN, "");
        if (accessToken == null || accessToken.isEmpty()) {
            requestLogin();
        }else {
            initLoginInfo();
        }

    }

    private void initLoginInfo() {
    }


    private void requestLogin() {
        ActivitySwitcher.switchTo(this, AuthorizeActivity.class);
    }


}
