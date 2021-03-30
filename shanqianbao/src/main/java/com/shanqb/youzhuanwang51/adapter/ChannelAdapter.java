package com.shanqb.youzhuanwang51.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import com.shanqb.youzhuanwang51.R;
import com.shanqb.youzhuanwang51.bean.ChannelBean;
import com.shanqb.youzhuanwang51.test.BaseRecyclerViewAdapter;
import com.shanqb.youzhuanwang51.view.RoundCornerImageView;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xui.widget.imageview.strategy.DiskCacheStrategyEnum;

import java.util.List;

public class ChannelAdapter extends BaseRecyclerViewAdapter {

    private List<ChannelBean> channelBeanList;
    /**
     * 默认加载图片
     */
    private ColorDrawable mColorDrawable;

    public ChannelAdapter(List<ChannelBean> channelBeanList) {
        super(R.layout.item_layout_channel);
        this.channelBeanList = channelBeanList;
        mColorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void onBindViewData(ViewHolder var1, int var2) {
        try {
            ChannelBean channelBean = channelBeanList.get(var2);
            RoundCornerImageView imageView = (RoundCornerImageView) var1.getImageView(R.id.channel_radiusImgView);
//            ImageLoader.get().loadImage(imageView,channelBean.getImageUrl());
            ImageLoader.get().loadImage(imageView, channelBean.getImageUrl(), mColorDrawable,
                    true ? DiskCacheStrategyEnum.RESOURCE : DiskCacheStrategyEnum.NONE);
//            imageView.setImageResource(R.drawable.shiwang01);
//            ImageLoader.get().loadImage(imageView,R.drawable.shiwang01);
//            imageView.setImageURI(Uri.parse(channelBean.getImageUrl()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int size = channelBeanList==null?0:channelBeanList.size();
        Log.e(getClass().getName(), "getItemCount() called size="+size);
        return size;
//        return 4;
    }


}
