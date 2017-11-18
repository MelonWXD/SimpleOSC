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

import com.dongua.simpleosc.R;


public class NavigationItem extends FrameLayout {

    //正常显示
    public static final int TYPE_NORMAL = 0x01;
    //不显示文字
    public static final int TYPE_NO_TITLE = 0x02;
    //不显示文字 ImageView填充
    public static final int TYPE_IMAGE_FILL = 0x03;

    private ImageView mIcon;
    private TextView mTitle;
    private TextView mDot;

    private Class mClass;
    private String mTag;
    private Fragment mFragment;


    private int mType = TYPE_NORMAL;

    public NavigationItem(Context context) {
        super(context);
        init();
    }

    public NavigationItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NavigationItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.layout_nav_item, this, true);
//
        mIcon = findViewById(R.id.iv_nav_icon);
        mTitle = findViewById(R.id.tv_nav_title);
        mDot = findViewById(R.id.tv_nav_dot);
    }

    public void init(@DrawableRes int resId, @StringRes int strId,Class clz) {
        mIcon.setImageResource(resId);
        mTitle.setText(strId);
        mClass = clz;
        mTag = mClass.getName();
    }

    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mIcon.setSelected(selected);
        mTitle.setSelected(selected);
    }

    public void showRedDot(int count) {
        mDot.setVisibility(count > 0 ? VISIBLE : GONE);
        mDot.setText(String.valueOf(count));
    }


    public String getTag() {
        return mTag;
    }


    public Class getClx() {
        return mClass;
    }

    public void setClx(Class mClass) {
        this.mClass = mClass;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }
}
