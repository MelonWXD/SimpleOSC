package com.dongua.simpleosc.ui.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.dongua.simpleosc.R;
import com.orhanobut.logger.Logger;


/**
 * author: Lewis
 * data: On 18-1-25.
 */

public class NameImageView extends AppCompatImageView {

    private Context mContext;
    private String mName = "你";
    private BitmapShader mBitmapShader;
    private Paint mTextPaint;
    private Paint mBitmapPaint;
    private Matrix mScaleMatrix;
    private int mWidth;
    private int mHeight;
    private int mRadius;

    private int mTextSize;

    public NameImageView(Context context) {
        super(context);
        init(context);

    }

    public NameImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public NameImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        mContext = context;

        mScaleMatrix = new Matrix();

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/*
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mRadius = Math.min(mWidth, mHeight)/ 2;

//        setMeasuredDimension(mWidth, mWidth);
*/

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
//        Logger.i("qqwxd:"+mWidth+","+mHeight);
        mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mRadius = mWidth / 2;
        mTextSize = mRadius;
        setMeasuredDimension(mWidth, mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
//            mTextPaint.setTextSize();

            mTextPaint.setColor(ContextCompat.getColor(mContext,R.color.pink900));
            canvas.drawCircle(mWidth / 2, mWidth / 2, mRadius, mTextPaint);
            mTextPaint.setColor(ContextCompat.getColor(mContext,R.color.white));
            mTextPaint.setTextSize(mTextSize);
            canvas.drawText(mName, mWidth / 2 - mTextSize / 2, mWidth / 2 + mTextSize / 3, mTextPaint);

        } else {
            setUpShader();
            canvas.drawCircle(mWidth / 2, mWidth / 2, mRadius, mBitmapPaint);
            // drawSomeThing(canvas);

        }


        mBitmapPaint.setStyle(Paint.Style.STROKE);
        mBitmapPaint.setStrokeWidth(6f);
        mBitmapPaint.setColor(ContextCompat.getColor(mContext,R.color.white));
        canvas.drawCircle(mWidth / 2, mWidth / 2, mRadius-3, mBitmapPaint);

    }


    /**
     * 初始化BitmapShader
     */
    private void setUpShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        Bitmap bmp = drawableToBitamp(drawable);
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        mBitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        // 拿到bitmap宽或高的小值
        int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
        scale = mWidth * 1.0f / bSize;

        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mScaleMatrix.setScale(scale, scale);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mScaleMatrix);
        // 设置shader
        mBitmapPaint.setShader(mBitmapShader);
    }

    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }


}
