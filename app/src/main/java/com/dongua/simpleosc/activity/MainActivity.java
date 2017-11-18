package com.dongua.simpleosc.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.dongua.simpleosc.R;
import com.dongua.simpleosc.nav.BottomNavFragment;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
//
//    @BindView(R.id.bnb_main)
//    BottomNavigationBar mNavigationBar;
//    BottomNavigationItem mNavNews;
//    BottomNavigationItem mNavTweet;
//    BottomNavigationItem mNavPub;
//    BottomNavigationItem mNavDiscover;
//    BottomNavigationItem mNavMe;
//    BottomNavigationItem[] mNavItems = new BottomNavigationItem[]{mNavNews,mNavTweet,mNavPub,mNavDiscover,mNavMe};


    @BindView(R.id.main_container)
    FrameLayout mMainContainer;
//    @BindView(R.id.fag_nav)
    BottomNavFragment mBottomNavFragment;
    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        FragmentManager manager = getSupportFragmentManager();
        mBottomNavFragment = (BottomNavFragment)manager.findFragmentById(R.id.fag_nav);
        mBottomNavFragment.setup(this,manager,R.id.main_container);



//        mNavNews = new BottomNavigationItem(R.mipmap.ic_nav_news_normal,this.getString(R.string.nav_string_news));
//        mNavTweet = new BottomNavigationItem(R.mipmap.ic_nav_tweet_normal,this.getString(R.string.nav_string_tweet));
//        mNavDiscover = new BottomNavigationItem(R.mipmap.ic_nav_discover_normal,this.getString(R.string.nav_string_discover));
//        mNavMe = new BottomNavigationItem(R.mipmap.ic_nav_my_normal,this.getString(R.string.nav_string_my));
//        mNavPub = new BottomNavigationItem(R.mipmap.ic_nav_pub_normal,"");
//
//        mNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
//        mNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
//        mNavigationBar.setBarBackgroundColor(R.color.white);
//
//        int normalColor = this.getResources().getColor(R.color.green100);
//        int pressColor = this.getResources().getColor(R.color.green800);
//        int pubColor = this.getResources().getColor(R.color.green900);
//
//        mNavigationBar.addItem(mNavNews.setActiveColor(pressColor).setInActiveColor(normalColor))
//                .addItem(mNavTweet.setActiveColor(pressColor).setInActiveColor(normalColor))
//                .addItem(mNavPub.setActiveColor(pressColor).setInActiveColor(pressColor))
//                .addItem(mNavMe.setActiveColor(pressColor).setInActiveColor(normalColor))
//                .addItem(mNavDiscover.setActiveColor(pressColor).setInActiveColor(normalColor))
//                .initialise();//所有的设置需在调用该方法前完成
//
//        mNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
//            @Override
//            public void onTabSelected(int position) {
//                Logger.d(position);
//            }
//            @Override
//            public void onTabUnselected(int position) {
//                Logger.d(position);
//            }
//            @Override
//            public void onTabReselected(int position) {
//                Logger.d(position);
//            }
//        });

    }
}
