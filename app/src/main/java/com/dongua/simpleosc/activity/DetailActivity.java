package com.dongua.simpleosc.activity;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.activity.BaseToolBarActivity;
import com.dongua.simpleosc.base.fragment.BaseToolBarFragment;
import com.dongua.simpleosc.bean.NewsTab;
import com.dongua.simpleosc.net.RetrofitClient;
import com.dongua.simpleosc.utils.UIUtil;
import com.dongua.simpleosc.utils.Util;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * Created by duoyi on 17-12-23.
 */

public class DetailActivity extends BaseToolBarActivity implements View.OnClickListener {

    public static final String HREF = "href";
    public static final String TYPE = "type";
    String mUrl = "http://www.oschina.net/news/92172/eolinker-3-2-1";
    //    long newsID = 0L;
    //shoud be long type
    int newsID = 0;
    @BindView(R.id.wv_content)
    WebView mWebContent;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        super.initView();
        //声明WebSettings子类
        WebSettings webSettings = mWebContent.getSettings();

        mWebContent.setWebChromeClient(new WebChromeClient());
        mWebContent.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
            }
        });
        mWebContent.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebContent.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }


//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

    }

    @Override
    protected void initData() {
        super.initData();
        int id = getIntent().getIntExtra(HREF, 0);
        int type = getIntent().getIntExtra(TYPE, 0);
        if (id != 0) {
            requestByType(id, type);
        } else {
            UIUtil.showLongToast(this, "帖子id为0，无法显示页面");
        }

    }

    private void requestByType(int id, int type) {
        Observable<ResponseBody> observer;
        switch (type) {
            case NewsTab.TYPE_NEWS:
                observer = RetrofitClient.getInstance().getNewsDetail(id);
                break;
            case NewsTab.TYPE_BLOG:
                observer = RetrofitClient.getInstance().getBlogDetail(id);
                break;
            case NewsTab.TYPE_POST:
                observer = RetrofitClient.getInstance().getPostDetail(id);
                break;
            default:
                return;

        }
        observer.observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String rep = null;
                        try {
                            rep = responseBody.string();
                            mUrl = parseUrl(rep);
                            if (mUrl.contains("https"))
                                mUrl = mUrl.replace("https", "http");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Logger.i(mUrl);
                                    mWebContent.loadUrl(mUrl);

                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.i(throwable.getMessage());

                    }
                });
    }

    private String parseUrl(String rep) {
        JsonObject jo = Util.string2Json(rep);
        return jo.get("url").getAsString();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_detail;
    }

    @Override
    protected void setCustomToolbar(View toolbar) {
//        toolbar.setClickable(false);
//        RelativeLayout mainContent = toolbar.findViewById(R.id.tb_content);


        toolbar.findViewById(R.id.ll_back_tb).setOnClickListener(this);
//        toolbar.findViewById(R.id.tv_back_tb).setOnClickListener(this);
        toolbar.findViewById(R.id.iv_comment_tb).setVisibility(View.INVISIBLE);
        toolbar.findViewById(R.id.iv_share_tb).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Logger.i("clcikkkk" + v.getClass().getName());
        switch (v.getId()) {
            case R.id.ll_back_tb:
            case R.id.iv_back_tb:
            case R.id.tv_back_tb:
                onBackPressed();
                break;
            case R.id.iv_comment_tb:
                break;
            case R.id.iv_share_tb:
                break;
        }
    }
}
