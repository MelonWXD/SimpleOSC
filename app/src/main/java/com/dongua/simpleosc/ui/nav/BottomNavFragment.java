package com.dongua.simpleosc.ui.nav;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.activity.MainActivity;
import com.dongua.simpleosc.activity.PubActivity;
import com.dongua.simpleosc.base.fragment.BaseFragment;
import com.dongua.simpleosc.base.fragment.BaseViewPagerFragment;
import com.dongua.simpleosc.fragment.DiscoverFragment;
import com.dongua.simpleosc.fragment.MeFragment;
import com.dongua.simpleosc.fragment.NewsFragment;
import com.dongua.simpleosc.fragment.TweetFragment;
import com.dongua.simpleosc.utils.ActivitySwitcher;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Leiws on 17-11-18.
 */

public class BottomNavFragment extends BaseFragment {

    public static final String TAB_POSTION = "pos";
    public static final String TAB_SCROLL = "scroll";

    @BindView(R.id.nav_item_news)
    NavigationItem mNavNews;
    @BindView(R.id.nav_item_tweet)
    NavigationItem mNavTweet;
    @BindView(R.id.nav_item_discover)
    NavigationItem mNavDiscover;
    @BindView(R.id.nav_item_me)
    NavigationItem mNavMe;
    @BindView(R.id.nav_item_pub)
    ImageView mNavPub;


    private Context mContext;
    private int mContainerId;
    private FragmentManager mFragmentManager;
    private NavigationItem mCurrentNavItem;

    private List<NavigationItem> mNavItemList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mNavNews.init(R.drawable.tab_news_selector, R.string.nav_string_news, NewsFragment.class);
        mNavTweet.init(R.drawable.tab_tweet_selector, R.string.nav_string_tweet, TweetFragment.class);
        mNavDiscover.init(R.drawable.tab_discover_selector, R.string.nav_string_discover, DiscoverFragment.class);
        mNavMe.init(R.drawable.tab_me_selector, R.string.nav_string_me, MeFragment.class);


        mNavItemList.add(mNavNews);
        mNavItemList.add(mNavTweet);
        mNavItemList.add(mNavDiscover);
        mNavItemList.add(mNavMe);

    }


    public void setFragments(List<BaseFragment> mFragmentList) {
        for (BaseFragment fragment : mFragmentList) {
            setFragment(fragment);
        }
    }

    public void setFragment(BaseFragment fragment) {
        if (fragment instanceof NewsFragment)
            mNavNews.setFragment(fragment);
        else if (fragment instanceof TweetFragment) {
            mNavTweet.setFragment(fragment);
        } else if (fragment instanceof DiscoverFragment) {
            mNavDiscover.setFragment(fragment);
        } else if (fragment instanceof MeFragment) {
            mNavMe.setFragment(fragment);
        }
    }

    @OnClick({R.id.nav_item_pub, R.id.nav_item_news, R.id.nav_item_tweet,
            R.id.nav_item_discover, R.id.nav_item_me})
    public void onClick(View view) {
        if (view instanceof NavigationItem) {
            NavItemSelect((NavigationItem) view);
        } else {
            PubItemClick();
        }
    }

    private void NavItemSelect(NavigationItem newNavItem) {
        NavigationItem oldNavItem = null;
        if (mCurrentNavItem != null) {
            oldNavItem = mCurrentNavItem;
            if (oldNavItem == newNavItem) {
                onReselect(oldNavItem);
                return;
            }
            oldNavItem.setSelected(false);
        }
        newNavItem.setSelected(true);
        mCurrentNavItem = newNavItem;
        doTabChanged(oldNavItem, newNavItem);


    }

    private void onReselect(NavigationItem oldNavItem) {

    }

    private void doTabChanged(NavigationItem oldNavItem, NavigationItem newNavItem) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (oldNavItem != null) {
            if (oldNavItem.getFragment() != null) {
                ft.detach(oldNavItem.getFragment());
            }
        }
        if (newNavItem != null) {
            if (newNavItem.getFragment() == null) {
                BaseFragment fragment = (BaseFragment) Fragment.instantiate(mContext, newNavItem.getTag(), null);
                fragment.setRetainInstance(true);
                ft.add(mContainerId, fragment, newNavItem.getTag());
                newNavItem.setFragment(fragment);
            } else {
                ft.attach(newNavItem.getFragment());
            }
        }
        ft.commit();

        //change tab title
        MainActivity act = (MainActivity) getActivity();

//        if (newNavItem.getId() == R.id.nav_item_me) {
//            act.getToolBar().setVisibility(View.GONE);
//        }else {
//            act.getToolBar().setVisibility(View.VISIBLE);
//        }
//        else {
//            act.setToolBarVisibility(View.VISIBLE);
//
//            act.setToolBarTitle(newNavItem.getTitle());
//        }
    }


    private void PubItemClick() {
        ActivitySwitcher.switchTo(getActivity(), PubActivity.class);
        getActivity().overridePendingTransition(0, 0);
    }



    public void setup(Context context, FragmentManager manager, int content_id, Bundle state) {
        mContext = context;
        mFragmentManager = manager;
        mContainerId = content_id;


        if (state != null) {
            int pos = state.getInt(TAB_POSTION, 0);
//            Logger.d(pos);

            NavItemSelect(mNavItemList.get(pos));
            if (mNavItemList.get(pos).getFragment() instanceof BaseViewPagerFragment) {
                BaseViewPagerFragment fragment = (BaseViewPagerFragment) mNavItemList.get(pos).getFragment();
                fragment.setScroll(state.getInt(TAB_SCROLL, 0));
//                Logger.d(state.getInt(TAB_SCROLL, 0));
            }
        } else {
            NavItemSelect(mNavItemList.get(0));
        }
//        if (postion >= 0) {
//            NavItemSelect(mNavItemList.get(postion));
//        } else {
//            NavItemSelect(mNavNews);
//        }
    }


    public int getPostion() {
        return mNavItemList.indexOf(mCurrentNavItem);
    }


    public Bundle getStateBundle() {
        Bundle state = new Bundle();
        state.putInt(TAB_POSTION, mNavItemList.indexOf(mCurrentNavItem));
        if (mCurrentNavItem.getFragment() instanceof BaseViewPagerFragment) {
            BaseViewPagerFragment fragment = (BaseViewPagerFragment) mCurrentNavItem.getFragment();
            state.putInt(TAB_SCROLL, fragment.getScroll());
        }
        return state;
    }
}
