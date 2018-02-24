package com.dongua.simpleosc.activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.activity.BaseActivity;
import com.dongua.simpleosc.base.adapter.BaseRecyclerAdapter;
import com.dongua.simpleosc.utils.Util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

import static com.dongua.simpleosc.ui.tweet.VpTweetFragment.SUDOKU_NUMS;
import static com.dongua.simpleosc.ui.tweet.VpTweetFragment.SUDOKU_POSTION;
import static com.dongua.simpleosc.ui.tweet.VpTweetFragment.SUDOKU_URLS;

/**
 * author: Lewis
 * data: On 18-2-24.
 */

public class GalleryActivity extends BaseActivity {

    @BindView(R.id.rv_gallery)
    RecyclerView mGalleryView;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_gallery;
    }

    private List<String> urlList = new ArrayList<>();

    @Override
    protected void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle bundle = getIntent().getExtras();
        String urls = bundle.getString(SUDOKU_URLS);
        urlList.addAll(Arrays.asList(Util.splitImgUrls(urls)));


        int pos = bundle.getInt(SUDOKU_POSTION);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mGalleryView.setLayoutManager(linearLayoutManager);
        mGalleryView.setAdapter(new CardAdapter(this));
        new LinearSnapHelper().attachToRecyclerView(mGalleryView);

        mGalleryView.scrollToPosition(pos);

    }


    class CardAdapter extends BaseRecyclerAdapter<CardAdapter.ViewHolder> {

        private Context mContext;

        public CardAdapter(Context context) {
            super(context);
            mContext = context;
        }

        @Override
        protected int getItemLayoutID() {
            return R.layout.layout_gallery_item;
        }

        @Override
        protected ViewHolder getViewHolder(View root) {
            return new ViewHolder(root);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            Glide.with(mContext).load(urlList.get(position)).into(holder.imageView);

            //todo Glide下载图片再加载到SubsamplingScaleImageView
//            Glide.with(mContext)
//                    .load(imagePath).downloadOnly(new SimpleTarget<File>() {
//                @Override
//                public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
//                    // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
//                    ImageSource imageSource = ImageSource.uri(Uri.fromFile(resource));
//                    int  sWidth = BitmapFactory.decodeFile(resource.getAbsolutePath()).getWidth();
//                    int sHeight = BitmapFactory.decodeFile(resource.getAbsolutePath()).getHeight();
//                    WindowManager wm = (WindowManager) mContext
//                            .getSystemService(Context.WINDOW_SERVICE);
//                    int width = wm.getDefaultDisplay().getWidth();
//                    int height = wm.getDefaultDisplay().getHeight();
//                    float scale = SystemUtil.displaySize.x / (float) sWidth;
//                    //float centerX = SystemUtil.displaySize.x / 2;
//                    // imageView.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(2.0F, new PointF(0, 0), 0));
//                    //imageView.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(2.0F, new PointF(0, 0), 0));
//                    if (sHeight >= height
//                            && sHeight / sWidth >=3) {
//                        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
//                        imageView.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(2.0F, new PointF(0, 0), 0));
//                    }else {
//                        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
//                        imageView.setImage(ImageSource.uri(Uri.fromFile(resource)));
//                        imageView.setDoubleTapZoomStyle(ZOOM_FOCUS_CENTER_IMMEDIATE);
//                    }
//                }});


            //tv
            holder.textView.setText(String.format(getString(R.string.gallery_index), position + 1, urlList.size()));
            ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(holder.textView, "alpha", 0f, 1f);
            alphaAnimation.setDuration(500);
            alphaAnimation.setRepeatCount(0);
            alphaAnimation.setRepeatMode(ValueAnimator.REVERSE);
            alphaAnimation.setStartDelay(200);
            alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            alphaAnimation.start();
            //            AlphaAnimation alp = new AlphaAnimation(1.0f,0.0f);
//            alp.setDuration(2000);
//
//            holder.textView.setAnimation(alp);
        }

        @Override
        public int getItemCount() {
            return urlList.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
//            SubsamplingScaleImageView imageView;
            TextView textView;

            ViewHolder(final View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.iv_gallery_item);
                textView = itemView.findViewById(R.id.tv_index);
            }

        }

    }
}
