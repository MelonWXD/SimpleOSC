package com.dongua.simpleosc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dongua.simpleosc.ui.news.TabFragment;

import java.util.List;

/**
 * Created by duoyi on 17-11-27.
 */

public class ContentPagerAdapter extends FragmentPagerAdapter {

    private List<TabFragment> dataList;

    public ContentPagerAdapter(FragmentManager fm, List<TabFragment> data) {
        super(fm);
        dataList = data;
    }

    @Override
    public Fragment getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }
}
