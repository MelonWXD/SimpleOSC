package com.dongua.simpleosc.nav;

import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by JuQiu
 * on 16/8/18.
 */
public class NavigationButton extends FrameLayout {


    private ImageView mIconView;
    private TextView mTitleView;
    private TextView mDot;
    private String mTag;

    public NavigationButton(Context context) {
        super(context);
        init();
    }

    public NavigationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NavigationButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        inflater.inflate(R.layout.layout_nav_item, this, true);
//
//        mIconView = (ImageView) findViewById(R.id.nav_iv_icon);
//        mTitleView = (TextView) findViewById(R.id.nav_tv_title);
//        mDot = (TextView) findViewById(R.id.nav_tv_dot);
    }

    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mIconView.setSelected(selected);
        mTitleView.setSelected(selected);
    }

    public void showRedDot(int count) {
        mDot.setVisibility(count > 0 ? VISIBLE : GONE);
        mDot.setText(String.valueOf(count));
    }




    public String getTag() {
        return mTag;
    }


}
