package com.dongua.simpleosc.ui;

import android.annotation.SuppressLint;
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
import com.orhanobut.logger.Logger;

import java.util.Locale;

/**
 * Created by duoyi on 17-12-11.
 */

public class LoadMoreView extends View {

    public static final int FAST = 10;
    public static final int MEDIUM = 15;
    public static final int SLOW = 20;


    private Context mContext;
    private int[] mSchemeColors;
    private boolean isLoading = false;
    private boolean isFirstDraw = true;
    private Paint mPaint;
    private Paint mPaint1;
    private int halfWidth = 0;
    private int drawWidth = 0;
    private int drawLeft = 0;
    private int drawRight = 0;
    private int drawTop = 0;
    private int drawBottom = 0;
    private int drawStep = 0;


    private int drawSpeed = MEDIUM;

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
        mPaint1 = new Paint();
        mPaint1.setStyle(Paint.Style.FILL);

    }

    public LoadMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public void setDrawSpeed(int drawSpeed) {
        this.drawSpeed = drawSpeed;
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

    @SuppressLint("DefaultLocale")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        int w = getMeasuredWidth();

        if (isFirstDraw) {
            isFirstDraw = false;
            mPaint.setColor(mSchemeColors[0]);
            canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), mPaint);
            halfWidth = getMeasuredWidth() / 2;
            drawStep = halfWidth / drawSpeed;
            drawTop = 0;
            drawBottom = getMeasuredHeight();
        } else {

            drawWidth += drawStep;
            drawLeft = halfWidth - drawWidth % halfWidth;
            drawRight = halfWidth + drawWidth % halfWidth;


            int lastColor = mSchemeColors[(drawWidth / halfWidth) % mSchemeColors.length];
            int curColor = mSchemeColors[(drawWidth / halfWidth + 1) % mSchemeColors.length];

            mPaint.setColor(lastColor);
            canvas.drawRect(0, drawTop, drawLeft, drawBottom, mPaint);
            canvas.drawRect(drawRight, drawTop, halfWidth * 2, drawBottom, mPaint);
            mPaint.setColor(curColor);
            canvas.drawRect(drawLeft, drawTop, drawRight, drawBottom, mPaint);

        }

//        Logger.d(String.format(Locale.getDefault(), "halfWidth = %d , drawStep = %d ,colorIdx = %d", halfWidth, drawStep, (drawWidth / halfWidth) % mSchemeColors.length + 1));
//        Logger.d(String.format("leftStart = %d , rightEnd = %d ", drawLeft,drawRight));

        postInvalidateDelayed(100);
    }
}
