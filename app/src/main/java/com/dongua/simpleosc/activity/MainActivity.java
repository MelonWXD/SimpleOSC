package com.dongua.simpleosc.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.activity.BaseActivity;
import com.dongua.simpleosc.base.activity.BaseToolBarActivity;
import com.dongua.simpleosc.base.fragment.BaseFragment;
import com.dongua.simpleosc.fragment.DiscoverFragment;
import com.dongua.simpleosc.fragment.MeFragment;
import com.dongua.simpleosc.fragment.NewsFragment;
import com.dongua.simpleosc.fragment.TweetFragment;
import com.dongua.simpleosc.ui.nav.BottomNavFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    public static final String BOTTOM_NAV_STATE = "BottomNavState";
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

    BottomNavFragment mBottomNavFragment;

    private int mTabPostion = 0;

    private NewsFragment mNewsFragment;
    private TweetFragment mTweetFragment;
    private DiscoverFragment mDiscoverFragment;
    private MeFragment mMeFragment;
    List<BaseFragment> mFragmentList = new ArrayList<>();

    private Bundle mBottomNavStateBundle;

    private Toolbar mToolBar;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }


//    @Override
    protected void setCustomToolbar(Toolbar toolbar) {
        mToolBar =toolbar;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    @Override
//    protected void initBundle(Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
////            mTabPostion = savedInstanceState.getInt("Pos", 0);
//            mBottomNavStateBundle = savedInstanceState.getBundle(BOTTOM_NAV_STATE);
//        }
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
//            mTabPostion = savedInstanceState.getInt("Pos", 0);
            mBottomNavStateBundle = outState.getBundle(BOTTOM_NAV_STATE);
        }
    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof BottomNavFragment) {
            mBottomNavFragment = (BottomNavFragment) fragment;
        }else if(fragment instanceof BaseFragment) {
            mFragmentList.add((BaseFragment)fragment);
        }
//        if (fragment instanceof NewsFragment)
//            mNewsFragment = (NewsFragment) fragment;
//
//        else if (fragment instanceof TweetFragment) {
//            mTweetFragment = (TweetFragment) fragment;
//
//        } else if (fragment instanceof DiscoverFragment) {
//            mDiscoverFragment = (DiscoverFragment) fragment;
//
//        } else if (fragment instanceof MeFragment) {
//            mMeFragment = (MeFragment) fragment;
//
//        }else if (fragment instanceof BottomNavFragment) {
//            mBottomNavFragment = (BottomNavFragment) fragment;
//        }
    }

    @Override
    protected void initView() {
        FragmentManager manager = getSupportFragmentManager();
        if (mBottomNavFragment == null) {
            mBottomNavFragment = (BottomNavFragment) manager.findFragmentById(R.id.fag_nav);
        }
        mBottomNavFragment.setFragments(mFragmentList);
        mBottomNavFragment.setup(this, manager, R.id.main_container, mBottomNavStateBundle);


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

//    public void setToolBarVisibility(int visibility) {
//        mToolBar.setVisibility(visibility);
//    }
//
//    public void setToolBarTitle(String title){
//        if(title!=null){
//           TextView tv = mToolBar.findViewById(R.id.tv_title);
//           tv.setText(title);
//        }
//    }

    public Toolbar getToolBar(){
        return mToolBar;
    }
}
