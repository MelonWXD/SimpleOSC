package com.dongua.simpleosc.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duoyi on 17-11-18.
 */

public class MeFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_tab;
    }

//    @BindView(R.id.btn_test)
//    Button btn_test;
    @BindView(R.id.tv_test)
    TextView tv_test;


//    @OnClick(R.id.btn_test)
//    @Override
//    public void onClick(View view) {
//        tv_test.setText("wxd");
//    }


}
