package com.dongua.simpleosc;

import android.content.Intent;
import android.text.TextUtils;

import com.dongua.simpleosc.activity.BaseActivity;
import com.dongua.simpleosc.activity.MainActivity;

/**
 * 应用启动界面
 */
public class LaunchActivity extends BaseActivity {
    @Override
    protected int getLayoutID() {
        return 0;
    }

    @Override
    protected void initData() {
        super.initData();
        // 在这里我们检测是否是新版本安装，如果是则进行老版本数据迁移工作
        // 该工作可能消耗大量时间所以放在自线程中执行

    }

    private void doMerge() {


        // Delay...
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 完成后进行跳转操作
        redirectTo();
    }

    private void redirectTo() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
