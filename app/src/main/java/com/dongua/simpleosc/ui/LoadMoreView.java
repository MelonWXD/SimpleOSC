package com.dongua.simpleosc.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.dongua.simpleosc.R;

/**
 * Created by duoyi on 17-12-11.
 */

public class LoadMoreView extends View {

    private Context mContext;
    private int[] mSchemeColors;
    private boolean isLoading = false;
    private boolean isFirstDraw = true;
    private Paint mPaint;

    public LoadMoreView(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        mContext = context;

        setColorSchemeResources(R.color.red400, R.color.deeppurple400,
                R.color.green400, R.color.blue400);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);

    }

    public LoadMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }


    public void setColorSchemeResources(@ColorRes int... colorResIds) {
        final Context context = getContext();
        int[] colorRes = new int[colorResIds.length];
        for (int i = 0; i < colorResIds.length; i++) {
            colorRes[i] = ContextCompat.getColor(context, colorResIds[i]);
        }
        setSchemeColors(colorRes);
    }

    public void setSchemeColors(int[] colorSchemeColors) {
        this.mSchemeColors = colorSchemeColors;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getMeasuredWidth();
        if (isFirstDraw) {
            isFirstDraw = false;
            mPaint.setColor(mSchemeColors[0]);
            canvas.drawRect(getLeft(),getTop(),getRight(),getBottom(),mPaint);
        } else {
            for (int i = 1; i < w; i++) {

            }
        }

        postInvalidate();
    }
}
