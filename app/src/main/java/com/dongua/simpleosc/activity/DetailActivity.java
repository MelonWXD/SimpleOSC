package com.dongua.simpleosc.activity;

import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.activity.BaseToolBarActivity;
import com.dongua.simpleosc.base.fragment.BaseToolBarFragment;

import butterknife.BindView;

/**
 * Created by duoyi on 17-12-23.
 */

public class DetailActivity extends BaseToolBarActivity {


    @BindView(R.id.wv_content)
    WebView mWebContent;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_detail;
    }

    @Override
    protected void setCustomToolbar(Toolbar toolbar) {

    }
}
