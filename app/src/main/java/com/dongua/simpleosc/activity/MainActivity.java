package com.dongua.simpleosc.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.dongua.simpleosc.R;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bnv_main)
    BottomNavigationView mNavigationView;

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
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_nav_news:
                        Logger.d(item.getItemId());
                        break;
                    case R.id.item_nav_tweet:
                        Logger.d(item.getItemId());
                        break;
                    case R.id.item_nav_discover:
                        Logger.d(item.getItemId());
                        break;
                    case R.id.item_nav_my:
                        Logger.d(item.getItemId());
                        break;
                    default:;
                }
                return true;
            }
        });

    }
}
