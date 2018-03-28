package com.dongua.simpleosc.activity;

import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.activity.BaseToolBarActivity;
import com.orhanobut.logger.Logger;

import java.util.Locale;
import java.util.concurrent.locks.Lock;

import butterknife.BindView;

import static com.dongua.simpleosc.ui.tweet.VpTweetFragment.TWEET_ACT_ID;
import static com.dongua.simpleosc.ui.tweet.VpTweetFragment.TWEET_USR_ID;

/**
 * author: Lewis
 * date: On 18-2-26.
 */

public class TweetDetailActivity extends BaseToolBarActivity {

    //    private final String TWEET_URL = "https://my.oschina.net/httpssl/tweet/%d";
    private final String TWEET_URL = "https://my.oschina.net/u/%d/tweet/%d";


    @BindView(R.id.wb_tweet)
    WebView mTweetWb;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_tweet_detail;
    }


    @Override
    protected void initView() {
        super.initView();


        int usrID = getIntent().getIntExtra(TWEET_USR_ID, 0);
        int tweetID = getIntent().getIntExtra(TWEET_ACT_ID, 0);


        WebSettings webSettings = mTweetWb.getSettings();

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

        mTweetWb.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
            }
        });
        String url = String.format(Locale.CHINA, TWEET_URL, usrID, tweetID);
        Logger.i(url);
        mTweetWb.loadUrl(url);
    }

    @Override
    protected void setCustomToolbar(View toolbar) {
        toolbar.findViewById(R.id.iv_comment_tb).setVisibility(View.GONE);
        toolbar.findViewById(R.id.iv_share_tb).setVisibility(View.GONE);
        toolbar.findViewById(R.id.ll_back_tb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
