package com.dongua.simpleosc.activity;

import android.webkit.WebView;

import com.dongua.simpleosc.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;

import static com.dongua.simpleosc.net.ApiService.APP_ID;
import static com.dongua.simpleosc.net.ApiService.BASE_URL;
import static com.dongua.simpleosc.net.ApiService.REDIRECT_URL;

/**
 * Created by duoyi on 17-11-24.
 */

public class AuthorizeActivity extends BaseActivity {

    @BindView(R.id.wb_oauth)
    WebView mWebView;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_authorize;
    }

    @Override
    protected void initView() {
        super.initView();
        String oauthURL =BASE_URL;
        try {
            oauthURL += "/action/oauth2/authorize?response_type=code&client_id="+APP_ID+"&redirect_uri="+ URLEncoder.encode(REDIRECT_URL,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mWebView.loadUrl(oauthURL);
    }
}
