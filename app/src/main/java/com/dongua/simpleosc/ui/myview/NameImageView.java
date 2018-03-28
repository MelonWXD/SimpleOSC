package com.dongua.simpleosc.ui.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dongua.simpleosc.R;
import com.orhanobut.logger.Logger;

import java.util.Random;


/**
 * author: Lewis
 * date: On 18-1-25.
 */

public class NameImageView extends AppCompatImageView {

    private Context mContext;
    //默认显示的text
    private String mName = "N";
    //位图编辑器
    private BitmapShader mBitmapShader;

    private Paint mOutlinePaint;
    private Paint mTextPaint;
    private Paint mBitmapPaint;
    //宽 高
    private int mWidth;
    private int mHeight;
    //圆形图片半径
    private int mRadius;
    //边缘宽度
    private int mBorderWidth;
    private final int DEFAULT_BORDER_WIDTH = 8;
    //边缘颜色
    private int mBorderColor;
    //文字大小
    private int mTextSize;
    //文字颜色
    private int mTextColor;
    //圆圈背景颜色
    private int mBackgroundColor;

    //随机颜色数组
    //pink900 purple900 indigo900 red900
    private int[] mDark = new int[]{0xFF880e4f, 0xFF4a148c, 0xFF1a237e, 0xFFb0120a};
    //white cyan300 lightgreen300 bluegrey300
    private int[] mTint = new int[]{0xFFFFFFFF, 0xFF4dd0e1, 0xFFaed581, 0xFF90a4ae};

    public NameImageView(Context context) {
        super(context);
        init(context);

    }

    public NameImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public NameImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NameImageView, defStyleAttr, 0);
        mBorderWidth = ta.getDimensionPixelSize(R.styleable.NameImageView_border_width, DEFAULT_BORDER_WIDTH);
//        mBorderColor = ta.getColor(R.styleable.NameImageView_border_color, mTint[getRandom()]);
//        mTextColor = ta.getColor(R.styleable.NameImageView_text_color, mTint[getRandom()]);
//        mBackgroundColor = ta.getColor(R.styleable.NameImageView_background_color, mDark[getRandom()]);
        mBorderColor = ta.getColor(R.styleable.NameImageView_border_color, mTint[0]);
        mTextColor = ta.getColor(R.styleable.NameImageView_text_color, mTint[0]);
        mBackgroundColor = ta.getColor(R.styleable.NameImageView_background_color, mDark[0]);
        ta.recycle();

        init(context);
    }

    private int getRandom() {
        return new Random().nextInt(4);
    }

    private void init(Context context) {

        mContext = context;

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);

        mOutlinePaint = new Paint();
        mOutlinePaint.setAntiAlias(true);

    }

    public void setName(String name) {
        if (name.length() > 1)
            this.mName = name.substring(0, 1);
        else
            this.mName = name;


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mWidth = Math.min(mWidth, mHeight);
        mRadius = mWidth / 2;
        mTextSize = mRadius;
        setMeasuredDimension(mWidth, mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //getBackground()
        if (getDrawable() == null) {
            //画字
            mTextPaint.setColor(mBackgroundColor);
            canvas.drawCircle(mRadius, mRadius, mRadius - mBorderWidth, mTextPaint);
            mTextPaint.setColor(mTextColor);
            mTextPaint.setTextSize(mTextSize);


            float textWidth = mTextPaint.measureText(mName);
            canvas.drawText(mName, mRadius - textWidth/2, mRadius + mTextSize / 3, mTextPaint);

            //debug
//            mTextPaint.setStrokeWidth(4);
//            mTextPaint.setColor(Color.BLUE);
//            canvas.drawLine(0, mRadius + mTextSize / 3, mRadius - mTextSize / 2, mRadius + mTextSize / 3, mTextPaint);
//            canvas.drawLine(mRadius - mTextSize / 2,0, mRadius - mTextSize / 2,mRadius + mTextSize / 3, mTextPaint);
//            mTextPaint.setColor(Color.YELLOW);
//            canvas.drawLine(0, mRadius, mRadius, mRadius, mTextPaint);

        } else {
            //画背景图
            setUpShader();
            canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
//            getDrawable().draw(canvas);//画图
        }

        //画外部的边缘
        mOutlinePaint.setStyle(Paint.Style.STROKE);
        mOutlinePaint.setStrokeWidth(mBorderWidth);
        mOutlinePaint.setColor(mBorderColor);
        canvas.drawCircle(mRadius, mRadius, mRadius - mBorderWidth / 2, mOutlinePaint);

    }


    /**
     * 设置BitmapShader
     */
    private void setUpShader() {
        Drawable drawable = getDrawable();
        Bitmap bmp = drawableToBitamp(drawable);
        mBitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //计算放缩比例
        int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
        float scale = mWidth * 1.0f / bSize;

        //设置放缩矩阵
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(matrix);

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
