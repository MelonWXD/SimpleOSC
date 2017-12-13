package com.dongua.simpleosc.fragment;

import android.widget.TextView;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.fragment.BaseFragment;

import butterknife.BindView;

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
