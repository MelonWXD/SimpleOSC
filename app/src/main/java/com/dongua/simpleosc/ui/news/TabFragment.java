package com.dongua.simpleosc.ui.news;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dongua.simpleosc.R;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by duoyi on 17-11-27.
 */

public class TabFragment extends BaseRecyclerFragment {

    public static final String BANNER_SHOW = "banner";

    @BindView(R.id.rv_banner)
    Banner mBanner;
    @BindView(R.id.rv_content)
    RecyclerView mRecyclerView;

    private boolean hasBanner = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sub_tab;
    }


    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        hasBanner = bundle.getBoolean(BANNER_SHOW, false);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        if (hasBanner) {
            mBanner.setImageLoader(new GlideImageLoader());
            List<String> imgUrl = new ArrayList<>();
            imgUrl.add("haha");
            mBanner.setImages(imgUrl);
            mBanner.start();
        }
    }

    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */


            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
            Logger.d("path="+path);


        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建

    }

}
