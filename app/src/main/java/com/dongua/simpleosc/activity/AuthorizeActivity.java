package com.dongua.simpleosc.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.dongua.simpleosc.R;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import butterknife.BindView;

import static com.dongua.simpleosc.LaunchActivity.CODE_AUTHORIZE;
import static com.dongua.simpleosc.LaunchActivity.CODE_KEY;
import static com.dongua.simpleosc.net.RetrofitClient.*;

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
        String oauthURL = BASE_URL;
        try {
            oauthURL += "/action/oauth2/authorize?response_type=code&client_id=" + APP_ID + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URL, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

//支持插件


//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式


        mWebView.setWebViewClient(new MyWebClient());
        mWebView.loadUrl(oauthURL);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void onAuthrized(String code) {
        Intent intent = new Intent();
        intent.putExtra(CODE_KEY, code);
        this.setResult(CODE_AUTHORIZE, intent);
        finish();

    }

    class MyWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {


            //regex = code=([\wd]+)
            if (url.contains(REDIRECT_URL) && url.contains("code")) {

                Pattern pattern = Pattern.compile("code=([\\w\\d]+)");
                Matcher matcher = pattern.matcher(url);
                while (matcher.find()) {
                    String code = matcher.group(1);
                    Logger.d("match code=" + code);
                    onAuthrized(code);
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
