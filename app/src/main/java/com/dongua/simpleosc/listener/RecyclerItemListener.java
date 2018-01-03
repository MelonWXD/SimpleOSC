package com.dongua.simpleosc.listener;

import android.view.View;

/**
 * Created by duoyi on 18-1-3.
 */

public interface RecyclerItemListener {
    void onClick(View view, int pos);

    boolean onLongClick(View view, int pos);
}
