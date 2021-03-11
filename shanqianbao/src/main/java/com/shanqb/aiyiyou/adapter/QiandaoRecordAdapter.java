package com.shanqb.aiyiyou.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shanqb.aiyiyou.R;
import com.shanqb.aiyiyou.bean.ChannelBean;
import com.shanqb.aiyiyou.bean.QiandaoItemBean;
import com.shanqb.aiyiyou.test.BaseRecyclerViewAdapter;
import com.shanqb.aiyiyou.view.RoundCornerImageView;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xui.widget.imageview.strategy.DiskCacheStrategyEnum;

import java.util.List;

public class QiandaoRecordAdapter extends BaseRecyclerViewAdapter {

    private List<QiandaoItemBean> qiandaoItemBeanList;
    /**
     * 默认加载图片
     */
    private ColorDrawable mColorDrawable;

    public QiandaoRecordAdapter(List<QiandaoItemBean> qiandaoItemBeanList) {
        super(R.layout.item_layout_qiandao);
        this.qiandaoItemBeanList = qiandaoItemBeanList;
        mColorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void onBindViewData(ViewHolder var1, int var2) {
        try {
            QiandaoItemBean qiandaoItemBean = qiandaoItemBeanList.get(var2);
            CheckBox checkBox = (CheckBox) var1.getButton(R.id.qiandaoStatus_checkbok);
            checkBox.setChecked(qiandaoItemBean.isQiandao());
            TextView textView = (TextView)var1.getTextView(R.id.zhouji_textView);
            textView.setText(qiandaoItemBean.getDay()+"");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int size = qiandaoItemBeanList ==null?0: qiandaoItemBeanList.size();
        Log.e(getClass().getName(), "getItemCount() called size="+size);
        return size;
//        return 4;
    }


}
