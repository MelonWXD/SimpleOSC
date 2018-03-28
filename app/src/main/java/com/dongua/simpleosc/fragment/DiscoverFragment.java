package com.dongua.simpleosc.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.adapter.BaseRecyclerAdapter;
import com.dongua.simpleosc.base.fragment.BaseFragment;
import com.dongua.simpleosc.base.fragment.BaseToolBarFragment;
import com.dongua.simpleosc.listener.RecyclerItemListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Leiws on 17-11-18.
 */

public class DiscoverFragment extends BaseToolBarFragment {


    @BindView(R.id.rv_discover)
    RecyclerView mRecyclerView;

    DiscoverRecyclerAdapter mAdapter;

    //    private List<String>
    private String[] itemNames = new String[]{"Gank.io", "玩Android"};
    private int[] itemIcons = new int[]{R.drawable.svg_gankio, R.drawable.svg_android};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover_tab;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mAdapter = new DiscoverRecyclerAdapter(getActivity());
        mAdapter.setItemListener(new RecyclerItemListener() {
            @Override
            public void onClick(View view, int pos) {

            }

            @Override
            public boolean onLongClick(View view, int pos) {
                return false;
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }


    class DiscoverRecyclerAdapter extends BaseRecyclerAdapter<DiscoverRecyclerAdapter.ItemHolder> {

        public DiscoverRecyclerAdapter(Context mContext) {
            super(mContext);
        }

        @Override
        public int getItemCount() {
            return itemNames.length;
        }

        @Override
        protected int getItemLayoutID() {
            return R.layout.layout_recycler_item_discover;
        }

        @Override
        protected ItemHolder getViewHolder(View root) {
            return new ItemHolder(root);
        }


        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            holder.itemView.setTag(position);

            holder.icon.setBackgroundResource(itemIcons[position]);
            holder.name.setText(itemNames[position]);
        }

        class ItemHolder extends RecyclerView.ViewHolder {
            ImageView icon;
            TextView name;

            ItemHolder(View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.iv_item_icon);
                name = itemView.findViewById(R.id.tv_item_name);
            }
        }
    }
}
