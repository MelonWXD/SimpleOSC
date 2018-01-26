package com.dongua.simpleosc.fragment;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongua.simpleosc.App;
import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.fragment.BaseFragment;
import com.dongua.simpleosc.bean.UserBean;
import com.dongua.simpleosc.db.UserBeanDao;
import com.dongua.simpleosc.net.RetrofitClient;
import com.dongua.simpleosc.ui.myview.NameImageView;
import com.dongua.simpleosc.utils.SharedPreferenceUtil;
import com.dongua.simpleosc.utils.Util;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by duoyi on 17-11-18.
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
    TextView mNameTextView;

    private int curUserID;
    private UserBean mUser;
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

        RetrofitClient.getInstance().getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        parseUser(responseBody.string());
                    }
                });

    }

    private void parseUser(String json) {
        mUser = (UserBean) Util.json2Bean(json, UserBean.class);
        SharedPreferenceUtil.put(CUR_USER_ID, mUser.getId());
        updateView(mUser);

        UserBean tmp = getUserById(mUser.getId());
        if(tmp!=null){
            mUser.setDbID(tmp.getDbID());
            App.getDaoSession().getUserBeanDao().update(mUser);
        }
        else
            App.getDaoSession().getUserBeanDao().save(mUser);
    }

    private void updateView(UserBean mUser) {
        mNameTextView.setText(mUser.getName());
        if (mUser.getAvatar() == null || mUser.getAvatar().equals(DEFAULT_AVATAR))
            mNameImageView.setmName(mUser.getName().substring(0, 1));
        else
            Glide.with(getContext()).load(mUser.getAvatar()).into(mNameImageView);

    }

    private UserBean getUserById(int id){
        return App.getDaoSession().getUserBeanDao().queryBuilder()
                .where(UserBeanDao.Properties.Id.eq(id))
                .unique();
    }


    //    @OnClick(R.id.btn_test)
//    @Override
//    public void onClick(View view) {
//        tv_test.setText("wxd");
//    }


}
