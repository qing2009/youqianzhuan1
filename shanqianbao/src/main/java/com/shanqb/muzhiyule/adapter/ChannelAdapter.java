package com.shanqb.muzhiyule.adapter;

import android.content.Context;
import android.util.Log;

import com.shanqb.muzhiyule.R;
import com.shanqb.muzhiyule.bean.ChannelBean;
import com.shanqb.muzhiyule.test.BaseRecyclerViewAdapter;
import com.shanqb.muzhiyule.view.RoundCornerImageView;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xui.widget.imageview.strategy.DiskCacheStrategyEnum;

import java.util.List;

public class ChannelAdapter extends BaseRecyclerViewAdapter {


    private List<ChannelBean> channelBeanList;
    /**
     * 默认加载图片
     */
//    private ColorDrawable mColorDrawable;

    public ChannelAdapter(Context context,List<ChannelBean> channelBeanList) {
        super(context,R.layout.item_layout_channel);
        this.channelBeanList = channelBeanList;
//        mColorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void onBindViewData(ViewHolder var1, int var2) {
        try {
            ChannelBean channelBean = channelBeanList.get(var2);
            RoundCornerImageView imageView = (RoundCornerImageView) var1.getImageView(R.id.channel_radiusImgView);
//            ImageLoader.get().loadImage(imageView,channelBean.getImageUrl());
            ImageLoader.get().loadImage(imageView, channelBean.getImageUrl(), context.getDrawable(R.drawable.jinqingqidai),
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
