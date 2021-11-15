package com.shanqb.myTool.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.shanqb.myTool.R;
import com.shanqb.myTool.bean.AppsBean;
import com.shanqb.myTool.test.BaseRecyclerViewAdapter;
import com.shanqb.myTool.view.CircleImageView;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xui.widget.imageview.strategy.DiskCacheStrategyEnum;

import java.util.List;

public class AppsListAdapter extends BaseRecyclerViewAdapter {

    private List<AppsBean> data;

    public AppsListAdapter(Context context, List<AppsBean> data) {
        super(context,R.layout.hometop_record_item);
        this.data = data;

    }

    @Override
    public void onBindViewData(ViewHolder var1, int var2) {
        try {
            AppsBean appsBean = data.get(var2);
            TextView income_textView = (TextView) var1.getTextView(R.id.income_textView);
            income_textView.setText("立即下载");

            CircleImageView gameHeadImgView = (CircleImageView) var1.getImageView(R.id.headImageView);;
            ImageLoader.get().loadImage(gameHeadImgView, appsBean.getAppIcon(), context.getDrawable(R.drawable.ic_launcher),
                    true ? DiskCacheStrategyEnum.RESOURCE : DiskCacheStrategyEnum.NONE);

            TextView gameName_textView = (TextView) var1.getTextView(R.id.gameName_textView);
            gameName_textView.setText(appsBean.getAppName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int size = data==null?0:data.size();
        Log.e(getClass().getName(), "getItemCount() called size="+size);
        return size;
    }

}