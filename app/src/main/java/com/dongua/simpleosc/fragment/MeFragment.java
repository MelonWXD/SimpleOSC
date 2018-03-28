package com.dongua.simpleosc.fragment;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongua.simpleosc.App;
import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.fragment.BaseFragment;
import com.dongua.simpleosc.bean.DetailUserBean;
import com.dongua.simpleosc.db.DetailUserBeanDao;
import com.dongua.simpleosc.net.RetrofitClient;
import com.dongua.simpleosc.ui.myview.NameImageView;
import com.dongua.simpleosc.utils.SharedPreferenceUtil;
import com.dongua.simpleosc.utils.Util;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Leiws on 17-11-18.
 */

public class MeFragment extends BaseFragment {
    public static final String CUR_USER_ID = "cur_uid";
    public static final String DEFAULT_AVATAR = "https://www.oschina.net/img/portrait.gif";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_tab;
    }

    @BindView(R.id.niv_portrait)
    NameImageView mNameImageView;
    @BindView(R.id.tv_username)
    TextView mNameTv;
    @BindView(R.id.tv_tweet)
    TextView mTweetTv;
    @BindView(R.id.tv_favorite)
    TextView mFavoriteTv;
    @BindView(R.id.tv_following)
    TextView mFollowingTv;
    @BindView(R.id.tv_follower)
    TextView mFollowerTv;

    private int curUserID;
    private DetailUserBean mUser;
//    @BindView(R.id.btn_test)
//    Button btn_test;
//    @BindView(R.id.tv_test)
//    TextView tv_test;

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
    }

    @Override
    protected void initData() {

        curUserID = (int) SharedPreferenceUtil.get(CUR_USER_ID, -1);
        if (curUserID > 0) {
            mUser = getUserById(curUserID);
            if (mUser != null)
                updateView(mUser);

        }

        RetrofitClient.getInstance().getMyDetailInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        parseUser(responseBody.string());

                    }
                });

    }

    private void parseUser(String json) {
        Logger.i("parseUser in tID:" + Thread.currentThread().getId());
//        mUser = (DetailUserBean) Util.json2Bean(json, UserBean.class);


        JsonObject jo = Util.string2Json(json);
        int gender = jo.get("gender").getAsInt();
        int fansCount = jo.get("fansCount").getAsInt();
        int followersCount = jo.get("followersCount").getAsInt();
        int favoriteCount = jo.get("favoriteCount").getAsInt();
        int uid = Integer.parseInt(jo.get("uid").getAsString());
        String name = jo.get("name").getAsString();
        String joinTime = jo.get("joinTime").getAsString();
        String lastLoginTime = jo.get("lastLoginTime").getAsString();
        String city = jo.get("city").getAsString();
        String province = jo.get("province").getAsString();
        String portrait = jo.get("portrait").getAsString();

        Type type = new TypeToken<List<String>>() {}.getType();
//        JsonArray ja = jo.get("expertise").getAsJsonArray();
//        String jsonArray = jo.get("expertise").getAsJsonArray().getAsString();
//        String jsonArray2 = jo.get("platforms").getAsString();
        List<String> expertise = Util.jsonArray2BeanList(jo.get("expertise").getAsJsonArray(), type);
        List<String> platforms = Util.jsonArray2BeanList(jo.get("platforms").getAsJsonArray(), type);

        mUser = new DetailUserBean(uid, name, gender, portrait, city, province, expertise, platforms
                , joinTime, lastLoginTime, favoriteCount, followersCount, fansCount);


        SharedPreferenceUtil.put(CUR_USER_ID, mUser.getUid());
        updateView(mUser);

        DetailUserBean tmp = getUserById(mUser.getUid());
        if (tmp != null) {
            mUser.setDbID(tmp.getDbID());
            App.getDaoSession().getDetailUserBeanDao().update(mUser);
        } else
            App.getDaoSession().getDetailUserBeanDao().save(mUser);
    }
//    private void parseUser(String json) {
//        mUser = (UserBean) Util.json2Bean(json, UserBean.class);
//        SharedPreferenceUtil.put(CUR_USER_ID, mUser.getId());
//        updateView(mUser);
//
//        UserBean tmp = getUserById(mUser.getId());
//        if (tmp != null) {
//            mUser.setDbID(tmp.getDbID());
//            App.getDaoSession().getUserBeanDao().update(mUser);
//        } else
//            App.getDaoSession().getUserBeanDao().save(mUser);
//    }

    private void updateView(final DetailUserBean mUser) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNameTv.setText(mUser.getName());
                if (mUser.getPortrait() == null || mUser.getPortrait().equals(DEFAULT_AVATAR))
                    mNameImageView.setName(mUser.getName().substring(0, 1));
                else
                    Glide.with(getContext()).load(mUser.getPortrait()).into(mNameImageView);

                mFavoriteTv.setText(String.valueOf(mUser.getFavoriteCount()));
                mFollowingTv.setText(String.valueOf(mUser.getFollowersCount()));
                mFollowerTv.setText(String.valueOf(mUser.getFansCount()));
            }
        });


    }

    private DetailUserBean getUserById(int uid) {
        return App.getDaoSession().getDetailUserBeanDao().queryBuilder()
                .where(DetailUserBeanDao.Properties.Uid.eq(uid))
                .unique();
    }


    //    @OnClick(R.id.btn_test)
//    @Override
//    public void onClick(View view) {
//        tv_test.setText("wxd");
//    }


}
