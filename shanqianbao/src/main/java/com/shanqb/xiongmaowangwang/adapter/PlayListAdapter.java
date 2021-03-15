package com.shanqb.xiongmaowangwang.adapter;

import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.shanqb.xiongmaowangwang.R;
import com.shanqb.xiongmaowangwang.bean.TryPlayListResponse;
import com.shanqb.xiongmaowangwang.test.BaseRecyclerViewAdapter;
import com.shanqb.xiongmaowangwang.utils.BitmapCache;

import java.util.List;

public class PlayListAdapter extends BaseRecyclerViewAdapter {

    List<TryPlayListResponse.DataBean> data;
    RequestQueue mQueue;
    public PlayListAdapter(RequestQueue mQueue) {
        super(R.layout.tryplay_record_item);
        this.mQueue = mQueue;
    }

    @Override
    public void onBindViewData(ViewHolder var1, int var2) {
        try {
            TryPlayListResponse.DataBean bean = data.get(var2);
            if (bean != null) {
                ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
                NetworkImageView gameIconImgView = (NetworkImageView) var1.getImageView(R.id.gameIcon_imageView);
                gameIconImgView.setImageUrl(bean.getAppIcon(), imageLoader);
                TextView income_textView = (TextView) var1.getTextView(R.id.income_textView);
                income_textView.setText("+" + bean.getUserFee() + "元");
                TextView gameName_textView = (TextView) var1.getTextView(R.id.gameName_textView);
                gameName_textView.setText(bean.getAdName());
                TextView gameDes_textView = (TextView) var1.getTextView(R.id.gameDes_textView);
                gameDes_textView.setText("完成任务：" + bean.getTaskName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<TryPlayListResponse.DataBean> data) {
        this.data = data;
    }
}