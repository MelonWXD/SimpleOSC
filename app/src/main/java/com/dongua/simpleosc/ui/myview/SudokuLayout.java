package com.dongua.simpleosc.ui.myview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dongua.simpleosc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Lewis
 * data: On 18-2-1.
 */

public class SudokuLayout extends ViewGroup implements View.OnClickListener {
    private static final String TAG = "wxd";
    private static final int SINGLE_MAX_W = 200;
    private static final int SINGLE_MAX_H = 220;
    private static final int SINGLE_MIN_W = 34;
    private static final int SINGLE_MIN_H = 34;

    public static final int FOUR_PIC_SIZE = 120;


    private static final int DEFAULT_ROW = 3;
    private static final int DEFAULT_COLUMN = 3;
    private static final int DEFAULT_DIVIDER = 4;

    private int row = DEFAULT_ROW;
    private int column = DEFAULT_COLUMN;
    private int vDivider;
    private int hDivider;

    private String[] urls;
    private List<Size> sizeList = new ArrayList<>();


    public SudokuLayout(Context context) {
        this(context, null);
    }

    public SudokuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SudokuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }


    public SudokuLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final Resources resources = getResources();
        final float density = resources.getDisplayMetrics().density;

        vDivider = (int) (DEFAULT_DIVIDER * density);
        hDivider = vDivider;

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SudokuLayout, defStyleAttr, defStyleRes);
            int r = ta.getInt(R.styleable.SudokuLayout_row, DEFAULT_ROW);
            row = r > 4 ? DEFAULT_ROW : r;
            int c = ta.getInt(R.styleable.SudokuLayout_column, DEFAULT_COLUMN);
            column = c > 10 ? DEFAULT_COLUMN : c;

            vDivider = ta.getDimensionPixelOffset(R.styleable.SudokuLayout_vDivider, vDivider);
            hDivider = ta.getDimensionPixelOffset(R.styleable.SudokuLayout_hDivider, hDivider);

            ta.recycle();
        }

    }

    public void setSize(int size) {
//        this.urls = urls;
        sizeList.clear();
        removeAllViews();

        for (int i = 0; i < size; i++) {
            View frameImg = LayoutInflater.from(getContext()).inflate(R.layout.layout_sudoku_image, null);
            frameImg.setTag(i);//for click
            frameImg.setOnClickListener(this);

            addView(frameImg);

            sizeList.add(loadIntoImage((ImageView) frameImg.findViewById(R.id.iv_picture), i));
            if (isGif(i)) {
                frameImg.findViewById(R.id.iv_is_gif).setVisibility(VISIBLE);
            }

        }

        if (getVisibility() == VISIBLE) {
            requestLayout();
        } else {
            setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure: ");
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int selfWidth = resolveSize(paddingLeft + paddingRight, widthMeasureSpec);
//        int selfWidth = getMeasuredWidth() - paddingLeft - paddingRight;
        int selfHeight = paddingTop + paddingBottom;//wrap_content h=0?

        int a = getMeasuredWidth();
        a = getMeasuredHeight();
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        final int childCount = getChildCount();
        if (childCount == 0) {

        } else if (childCount == 1) {

            View child = getChildAt(0);

//            int imageW = sizeList.get(0).w;
//            int imageH = sizeList.get(0).h;

            int childW = sizeList.get(0).w;
            int childH = sizeList.get(0).h;

            float density = getResources().getDisplayMetrics().density;
            float maxContentW = Math.min(selfWidth - paddingRight - paddingLeft, density * SINGLE_MAX_W);
            float maxContentH = density * SINGLE_MAX_H;


            //根据ImageView来填充
            if (childW > maxContentW) {
                childH = (int) (childH * maxContentW / childW);
                childW = (int) maxContentW;
            }
            if (childH > maxContentH) {
                childW = (int) (childW * maxContentH / childH);
                childH = (int) maxContentH;

            }

            //           铺满View所调用的
//            float hToW = imageH / (float) imageW;
//            if (hToW > maxContentH / maxContentW) {
//                childW = (int) maxContentW;
//                childH = (int) (maxContentW * hToW);
//            } else {
//                childH = (int) maxContentH;
//                childW = (int) (maxContentH / hToW);
//            }


            int minW = (int) (SINGLE_MIN_W * density);
            if (childW < minW)
                childW = minW;
            int minH = (int) (SINGLE_MIN_H * density);
            if (childH < minH)
                childH = minH;


            child.measure(MeasureSpec.makeMeasureSpec(childW, MeasureSpec.EXACTLY)
                    , MeasureSpec.makeMeasureSpec(childH, MeasureSpec.EXACTLY));

            selfHeight += childH;
        } else if (childCount == 4) {
            float density = getResources().getDisplayMetrics().density;

            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                child.measure(MeasureSpec.makeMeasureSpec((int) (density*FOUR_PIC_SIZE), MeasureSpec.EXACTLY)
                        , MeasureSpec.makeMeasureSpec((int) (density*FOUR_PIC_SIZE), MeasureSpec.EXACTLY));
            }
            selfHeight += density*FOUR_PIC_SIZE * 2 + vDivider;

        } else {
            float maxContentW = selfWidth - paddingRight - paddingLeft - hDivider * (column - 1);
            float childSize = maxContentW / column;
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                child.measure(MeasureSpec.makeMeasureSpec((int) childSize, MeasureSpec.EXACTLY)
                        , MeasureSpec.makeMeasureSpec((int) childSize, MeasureSpec.EXACTLY));
            }

            int lines = Math.round(childCount / (float) column);
            selfHeight += childSize * lines + vDivider * (lines - 1);

        }

        setMeasuredDimension(selfWidth, resolveSize(selfHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        final int childCount = getChildCount();

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        if (childCount == 0) {

        } else if (childCount == 1) {
            View childView = getChildAt(0);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            Log.i(TAG, "childView.getMeasuredHeight: " + childHeight);

            childView.layout(paddingLeft, paddingTop, paddingLeft + childWidth, paddingTop + childHeight);

        } else if (childCount == 4) {

            int childT = paddingTop;
            int childL = paddingLeft;

            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
//


                child.layout(childL, childT, childL + width, childT + height);
                childL += width+hDivider;

                if ((i+1) % 2 == 0) {
                    childL = paddingLeft;
                    childT += height + vDivider;
                }
            }

        } else {
            int selfWidth = r - l;
            int paddingRight = getPaddingRight();
            int childT = paddingTop;
            int childL = paddingLeft;

            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
//


                child.layout(childL, childT, childL + width, childT + height);
                childL += width+hDivider;

                if ((i+1) % column == 0) {
                    childL = paddingLeft;
                    childT += height + vDivider;
                }
            }
        }
    }


    class Size {
        int w;
        int h;

        public Size(int w, int h) {
            this.w = w;
            this.h = h;
        }
    }


    @Override
    public void onClick(View v) {
        onImageClick((int) v.getTag());
    }

    /**
     * @param postion 被点击的ImageView的位置
     */
    public void onImageClick(int postion) {
    }


    /**
     * @param imageView 显示图片的ImageView
     * @param pos       ImageView位置
     */
    public Size loadIntoImage(ImageView imageView, int pos) {
        String url = "https://www.easyicon.net/api/resizeApi.php?id=1185357&size=128";
        String url2 = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1517473777&di=0ea0eb3a1089b008eda43b8bade14a76&src=http://pic29.photophoto.cn/20131204/0034034499213463_b.jpg";
        String url3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517486139990&di=40602e9baa4f41be77734ae5938983d7&imgtype=0&src=http%3A%2F%2Fwww.aiimg.com%2Fuploads%2Fuserup%2F0904%2F230523213404.jpg";

        List<String> urls = new ArrayList<>();
        urls.add(url);
        urls.add(url2);
        urls.add(url3);
        urls.add(url);
        urls.add(url);
        urls.add(url3);
        Glide.with(getContext())
                .load(urls.get(pos))
                .apply(new RequestOptions().override(400, 400))
                .into(imageView);
        return new Size(400, 400);
    }


    /**
     * @param pos ImageView位置
     * @return ImageView是否为Gif图
     */
    public boolean isGif(int pos) {
        return false;
    }
}
