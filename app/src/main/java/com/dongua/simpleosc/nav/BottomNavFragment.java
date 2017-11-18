package com.dongua.simpleosc.nav;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.fragment.BaseFragment;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duoyi on 17-11-18.
 */

public class BottomNavFragment extends BaseFragment implements View.OnClickListener {

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


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mNavNews.init(R.drawable.tab_news_selector, R.string.nav_string_news,NewsFragment.class);
        mNavTweet.init(R.drawable.tab_tweet_selector, R.string.nav_string_tweet,TweetFragment.class);
        mNavDiscover.init(R.drawable.tab_discover_selector, R.string.nav_string_discover,DiscoverFragment.class);
        mNavMe.init(R.drawable.tab_me_selector, R.string.nav_string_me,MeFragment.class);
    }

    @OnClick({R.id.nav_item_pub, R.id.nav_item_news, R.id.nav_item_tweet,
            R.id.nav_item_discover, R.id.nav_item_me})
    @Override
    public void onClick(View view) {
        if (view instanceof NavigationItem) {
            NavItemClick((NavigationItem)view);
        } else {
            PubItemClick();
        }
    }

    private void NavItemClick(NavigationItem item) {
        Logger.d(item.getTag());
    }

    private void PubItemClick() {
    }



    public void setup(Context context, FragmentManager manager, int main_container_id) {
        mContext = context;
        mFragmentManager = manager;
        mContainerId = main_container_id;
    }
}
