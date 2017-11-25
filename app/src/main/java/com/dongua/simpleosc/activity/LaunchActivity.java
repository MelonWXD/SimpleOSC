package com.dongua.simpleosc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.BasePresenter;
import com.dongua.simpleosc.base.BaseView;
import com.dongua.simpleosc.net.RetrofitClient;
import com.dongua.simpleosc.utils.ActivitySwitcher;
import com.dongua.simpleosc.utils.SharedPreferenceUtil;
import com.dongua.simpleosc.utils.UIUtil;
import com.dongua.simpleosc.utils.Util;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;

import java.io.IOException;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.dongua.simpleosc.utils.SharedPreferenceUtil.ACCESS_TOKEN;
import static com.dongua.simpleosc.utils.SharedPreferenceUtil.REFRESH_TOKEN;

/**
 * Created by duoyi on 17-11-24.
 */

public class LaunchActivity extends BaseActivity {

    public static final int CODE_AUTHORIZE = 1;
    public static final String CODE_KEY = "code";

    AuthorizePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new AuthorizePresenter(this);
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


        String accessToken = (String) SharedPreferenceUtil.get(ACCESS_TOKEN, "");
        if (accessToken == null || accessToken.isEmpty()) {
            UIUtil.showLongToast(this, getString(R.string.hint_authorize));
            requestLogin(this);
//            mPresenter.getToken("code");
        } else {
            initLoginInfo();
        }

    }



    private void initLoginInfo() {
        Logger.d("找到本地授权tokne");
        ActivitySwitcher.switchTo(this,MainActivity.class);
    }


    private void requestLogin(final Activity act) {
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
            if (code != null || !code.isEmpty()) {
                mPresenter.getToken(code);
            }
        }

    }

    @Override
    public void setPresenter(Object presenter) {
        mPresenter = (AuthorizePresenter) presenter;
    }

    class AuthorizePresenter implements BasePresenter {

        private BaseView mView;

        public AuthorizePresenter(BaseView view) {
            mView = view;
//            mView.setPresenter(this);
        }

        @Override
        public Object getView() {
            return mView;
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
                                JsonObject jsonObject = Util.string2Json(responseBody.string());
                                String a_token = jsonObject.get("access_token").getAsString();
                                String r_token = jsonObject.get("refresh_token").getAsString();
                                Logger.d("Token:" + a_token);
                                Logger.d(r_token);
                                if (!a_token.isEmpty() && !r_token.isEmpty()) {
                                    SharedPreferenceUtil.put(ACCESS_TOKEN, a_token);
                                    SharedPreferenceUtil.put(REFRESH_TOKEN, r_token);
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


    }
}
