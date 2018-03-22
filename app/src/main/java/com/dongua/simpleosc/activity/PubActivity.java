package com.dongua.simpleosc.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.activity.BaseActivity;
import com.dongua.simpleosc.ui.news.NewsContract;
import com.dongua.simpleosc.utils.ActivitySwitcher;
import com.dongua.simpleosc.utils.UIUtil;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: Lewis
 * data: On 18-3-21.
 */

public class PubActivity extends BaseActivity {
    public static final int TYPE_LEFT = -1;
    public static final int TYPE_MID = 0;
    public static final int TYPE_RIGHT = 1;
    @BindView(R.id.ll_pub_article)
    LinearLayout articleLayout;
    @BindView(R.id.ll_pub_blog)
    LinearLayout blogLayout;
    @BindView(R.id.ll_pub_tweet)
    LinearLayout tweetLayout;
    @BindView(R.id.rl_main)
    RelativeLayout mainLayout;
    @BindView(R.id.btn_pub)
    ImageView pubButton;

    @OnClick({R.id.ll_pub_article, R.id.ll_pub_blog, R.id.ll_pub_tweet, R.id.rl_main, R.id.btn_pub})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_pub_article:
                UIUtil.showShortToast(this,"暂时只有发推特可以用，凑合用下");
                break;
            case R.id.ll_pub_blog:
                UIUtil.showShortToast(this,"暂时只有发推特可以用，凑合用下");
                break;
            case R.id.ll_pub_tweet:
//                ActivitySwitcher.switchTo();
                break;
            case R.id.rl_main:
            default:
                exit();
        }

    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_pub;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        enter();
    }

    private void enter() {
        pubButton.clearAnimation();
        pubButton.animate().rotation(135.0f).setDuration(200).start();
        translateAnim(articleLayout, TYPE_LEFT, true);
        translateAnim(blogLayout, TYPE_RIGHT, true);
        translateAnim(tweetLayout, TYPE_MID, true);

    }

    private void exit() {
        translateAnim(articleLayout, TYPE_LEFT, false);
        translateAnim(blogLayout, TYPE_RIGHT, false);
        translateAnim(tweetLayout, TYPE_MID, false);


        pubButton.animate().rotation(0).setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        }).start();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        exit();
    }


    private void translateAnim(final View view, final int type, boolean isOpen) {

        ValueAnimator valueAnimator;
        if (isOpen)
            valueAnimator = ValueAnimator.ofInt(0, 200);
        else
            valueAnimator = ValueAnimator.ofInt(200, 0);
        final float x, y;
        if (type == TYPE_LEFT) {
            x = -1 / (float) Math.sqrt(2);
            y = x;
        } else if (type == TYPE_RIGHT) {
            x = 1 / (float) Math.sqrt(2);
            y = -x;
        } else {
            x = 0;
            y = -1;
        }


        valueAnimator.setDuration(180);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int currentValue = (Integer) animator.getAnimatedValue();

                view.setTranslationX(currentValue * x * 2);
                view.setTranslationY(currentValue * y * 2);
                view.requestLayout();

            }
        });

        valueAnimator.start();

    }

}
