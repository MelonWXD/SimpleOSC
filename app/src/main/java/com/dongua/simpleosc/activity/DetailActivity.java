package com.dongua.simpleosc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.activity.BaseToolBarActivity;
import com.dongua.simpleosc.base.fragment.BaseToolBarFragment;
import com.dongua.simpleosc.utils.UIUtil;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

import static com.dongua.simpleosc.ui.news.SubFragment.HREF;

/**
 * Created by duoyi on 17-12-23.
 */

public class DetailActivity extends BaseToolBarActivity implements View.OnClickListener{


    @BindView(R.id.wv_content)
    WebView mWebContent;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_detail;
    }

    @Override
    protected void setCustomToolbar(View toolbar) {
//        toolbar.setClickable(false);
//        RelativeLayout mainContent = toolbar.findViewById(R.id.tb_content);


        toolbar.findViewById(R.id.iv_back_tb).setOnClickListener(this);
        toolbar.findViewById(R.id.tv_back_tb).setOnClickListener(this);
        toolbar.findViewById(R.id.iv_comment_tb).setOnClickListener(this);
        toolbar.findViewById(R.id.iv_share_tb).setOnClickListener(this);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = getIntent().getIntExtra(HREF,0);
        UIUtil.showShortToast(this,id+"");
    }

    @Override
    public void onClick(View v) {
        Logger.i("clcikkkk"+v.getClass().getName());
        switch (v.getId()){
            case R.id.iv_back_tb:
            case R.id.tv_back_tb:
//                        tv_back.setText("caca");
                onBackPressed();
                UIUtil.showShortToast(DetailActivity.this,"AD");
                break;
            case R.id.iv_comment_tb:
                break;
            case R.id.iv_share_tb:
                break;
        }
    }
}
