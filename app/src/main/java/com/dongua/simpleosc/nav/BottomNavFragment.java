package com.dongua.simpleosc.nav;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.fragment.BaseFragment;
import com.dongua.simpleosc.fragment.BaseRecyclerViewFragment;
import com.dongua.simpleosc.fragment.DiscoverFragment;
import com.dongua.simpleosc.fragment.MeFragment;
import com.dongua.simpleosc.fragment.NewsFragment;
import com.dongua.simpleosc.fragment.TweetFragment;
import com.dongua.simpleosc.nav.NavigationItem;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duoyi on 17-11-18.
 */

public class BottomNavFragment extends BaseFragment implements View.OnClickListener {

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
    @Override
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
    }


    private void PubItemClick() {
    }

    public void setup(Context context, FragmentManager manager, int main_container_id, Bundle state) {
        mContext = context;
        mFragmentManager = manager;
        mContainerId = main_container_id;


        if (state != null) {
            int pos = state.getInt(TAB_POSTION, 0);
            Logger.d(pos);

            NavItemSelect(mNavItemList.get(pos));
            if (mNavItemList.get(pos).getFragment() instanceof BaseRecyclerViewFragment) {
                BaseRecyclerViewFragment fragment = (BaseRecyclerViewFragment) mNavItemList.get(pos).getFragment();
                fragment.setScroll(state.getInt(TAB_SCROLL, 0));
                Logger.d(state.getInt(TAB_SCROLL, 0));
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



    public void setup(Context context, FragmentManager manager, int main_container_id) {
        mContext = context;
        mFragmentManager = manager;
        mContainerId = main_container_id;

        NavItemSelect(mNavItemList.get(0));
    }

    public int getPostion() {
        return mNavItemList.indexOf(mCurrentNavItem);
    }


    public Bundle getStateBundle() {
        Bundle state = new Bundle();
        state.putInt(TAB_POSTION, mNavItemList.indexOf(mCurrentNavItem));
        if (mCurrentNavItem.getFragment() instanceof BaseRecyclerViewFragment) {
            BaseRecyclerViewFragment fragment = (BaseRecyclerViewFragment) mCurrentNavItem.getFragment();
            state.putInt(TAB_SCROLL, fragment.getScroll());
        }
        return state;
    }
}
