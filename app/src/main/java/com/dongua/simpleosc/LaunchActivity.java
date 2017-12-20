package com.dongua.simpleosc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.dongua.simpleosc.activity.AuthorizeActivity;
import com.dongua.simpleosc.base.activity.BaseActivity;
import com.dongua.simpleosc.activity.MainActivity;
import com.dongua.simpleosc.net.RetrofitClient;
import com.dongua.simpleosc.utils.ActivitySwitcher;
import com.dongua.simpleosc.utils.SharedPreferenceUtil;
import com.dongua.simpleosc.utils.UIUtil;
import com.dongua.simpleosc.utils.Util;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Date;


import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.dongua.simpleosc.utils.SharedPreferenceUtil.ACCESS_TOKEN;
import static com.dongua.simpleosc.utils.SharedPreferenceUtil.REFRESH_TOKEN;
import static com.dongua.simpleosc.utils.SharedPreferenceUtil.TOKEN_EXPIRE;

/**
 * Created by duoyi on 17-11-24.
 */

public class LaunchActivity extends BaseActivity {

    public static final int CODE_AUTHORIZE = 1;
    public static final String CODE_KEY = "code";

//    AuthorizePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_launch;
    }


    protected void initPresenter() {
//        mPresenter = new AuthorizePresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }

        ViewGroup viewGroup = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        View childView = viewGroup.getChildAt(0);
        if (null != childView) {
            childView.setFitsSystemWindows(false);
        }


    }

    @Override
    protected void initData() {
        super.initData();


        String accessToken = (String) SharedPreferenceUtil.get(ACCESS_TOKEN, "");
        Logger.d(accessToken);
        if (accessToken == null || accessToken.isEmpty()) {
            UIUtil.showLongToast(this, getString(R.string.no_authorize));
            requestAuthorize(this);

        } else if (RetrofitClient.getInstance().isAccessExpire()) {

            accessToken = refreshToken();
            if (accessToken.isEmpty()) {
                UIUtil.showLongToast(this, getString(R.string.no_authorize));
                requestAuthorize(this);
                return;
            }
        }

        RetrofitClient.getInstance().setAccessToken(accessToken);
        initLoginInfo(this);


    }


    private void initLoginInfo(final Activity act) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ActivitySwitcher.switchTo(act, MainActivity.class);

            }
        }).start();
    }


    private void requestAuthorize(final Activity act) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//        ActivitySwitcher.switchTo(this, MainActivity.class);
//                ActivitySwitcher.switchTo(act, AuthorizeActivity.class);
                Intent intent = new Intent(act, AuthorizeActivity.class);
                startActivityForResult(intent, CODE_AUTHORIZE);
            }
        }).start();

    }

//    @Override
//    protected BasePresenter getPresenter(){
//        return
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_AUTHORIZE) {
            String code = data.getStringExtra(CODE_KEY);
            if (code != null && !code.isEmpty()) {
                getToken(code);
            }
        }

    }

    private String refreshToken() {

        final StringBuilder ret = new StringBuilder();
        RetrofitClient.getInstance().refreshToken()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String resp = responseBody.string();
                        Logger.d(resp);
                        ret.append(parseRespons(resp));

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d("刷新授权失败");
                    }
                });
        return ret.toString();
    }

    private void getToken(String code) {
        RetrofitClient client = RetrofitClient.getInstance();
        client.getToken(code)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        try {

                            String a_token = parseRespons(responseBody.string());
                            if (!a_token.isEmpty()) {
                                RetrofitClient.getInstance().setAccessToken(a_token);
                                initLoginInfo(LaunchActivity.this);
                            } else {
                                Logger.d(getString(R.string.authorize_falied));
                            }
                        } catch (IOException e) {
                            Logger.e(e.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.e(throwable.getMessage());
                    }
                });
    }


    private String parseRespons(String rep) {
        JsonObject jsonObject = Util.string2Json(rep);
        String a_token = jsonObject.get("access_token").getAsString();
        String r_token = jsonObject.get("refresh_token").getAsString();
        long expires = jsonObject.get("expires_in").getAsLong() * 1000;

        if (!a_token.isEmpty() && !r_token.isEmpty()) {
            SharedPreferenceUtil.put(ACCESS_TOKEN, a_token);
            SharedPreferenceUtil.put(REFRESH_TOKEN, r_token);
            SharedPreferenceUtil.put(TOKEN_EXPIRE, expires + new Date().getTime());

        }
        return a_token;
    }
//
//    class AuthorizePresenter implements BasePresenter {
//
//        private BaseView mView;
//
//        public AuthorizePresenter(BaseView view) {
//            mView = view;
////            mView.setPresenter(this);
//        }
//
//        @Override
//        public Object getView() {
//            return mView;
//        }
//
//        private void getToken(String code) {
//            RetrofitClient client = RetrofitClient.getInstance();
//            client.getToken(code)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(Schedulers.io())
//                    .subscribe(new Consumer<ResponseBody>() {
//                        @Override
//                        public void accept(ResponseBody responseBody) throws Exception {
//                            try {
//                                JsonObject jsonObject = Util.string2Json(responseBody.string());
//                                String a_token = jsonObject.get("access_token").getAsString();
//                                String r_token = jsonObject.get("refresh_token").getAsString();
//                                Logger.d("Token:" + a_token);
//                                Logger.d(r_token);
//                                if (!a_token.isEmpty() && !r_token.isEmpty()) {
//                                    SharedPreferenceUtil.put(ACCESS_TOKEN, a_token);
//                                    SharedPreferenceUtil.put(REFRESH_TOKEN, r_token);
//                                }
//                            } catch (IOException e) {
//                                Logger.e(e.getMessage());
//                            }
//                        }
//                    }, new Consumer<Throwable>() {
//                        @Override
//                        public void accept(Throwable throwable) throws Exception {
//                            Logger.e(throwable.getMessage());
//                        }
//                    });
//        }
//
//
//    }
}
