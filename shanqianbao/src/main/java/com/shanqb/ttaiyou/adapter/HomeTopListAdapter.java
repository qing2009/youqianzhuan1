package com.shanqb.ttaiyou.adapter;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.shanqb.ttaiyou.R;
import com.shanqb.ttaiyou.bean.TryPlayListResponse;
import com.shanqb.ttaiyou.test.BaseRecyclerViewAdapter;
import com.shanqb.ttaiyou.view.CircleImageView;
import com.xuexiang.xui.widget.imageview.ImageLoader;

import java.util.List;

public class HomeTopListAdapter extends BaseRecyclerViewAdapter {

    List<TryPlayListResponse.DataBean> data;
    String[] gameUserNameArr;
    int[] gameHeadArr;

    public HomeTopListAdapter(Context context) {

        super(R.layout.hometop_record_item);
        gameUserNameArr = context.getResources().getStringArray(R.array.gameUserName);
        gameHeadArr = new int[]{R.drawable.gameuserhead1,
                R.drawable.gameuserhead2,
                R.drawable.gameuserhead3,
                R.drawable.gameuserhead4,
                R.drawable.gameuserhead5,
                R.drawable.gameuserhead6,
                R.drawable.gameuserhead7,
                R.drawable.gameuserhead8,
                R.drawable.gameuserhead9,
                R.drawable.gameuserhead10,
                R.drawable.gameuserhead11,
                R.drawable.gameuserhead12,
                R.drawable.gameuserhead13,
                R.drawable.gameuserhead14,
                R.drawable.gameuserhead15,
                R.drawable.gameuserhead16,
                R.drawable.gameuserhead17,
                R.drawable.gameuserhead18,
                R.drawable.gameuserhead19,
                R.drawable.gameuserhead20,
                R.drawable.gameuserhead21,
                R.drawable.gameuserhead22,
                R.drawable.gameuserhead23,
                R.drawable.gameuserhead24,
                R.drawable.gameuserhead25,
                R.drawable.gameuserhead27,
                R.drawable.gameuserhead28,
                R.drawable.gameuserhead29
        };

    }

    @Override
    public void onBindViewData(ViewHolder var1, int var2) {
        try {
//            TryPlayListResponse.DataBean bean = data.get(var2);
//            if (bean != null) {
                TextView income_textView = (TextView) var1.getTextView(R.id.income_textView);
                income_textView.setText("¥" +((int)(Math.random()*100))/100.00+ "元");

                CircleImageView gameHeadImgView = (CircleImageView) var1.getImageView(R.id.headImageView);
                gameHeadImgView.setImageResource(gameHeadArr[(int)(Math.random()*100)%29]);

                TextView gameName_textView = (TextView) var1.getTextView(R.id.gameName_textView);
                gameName_textView.setText(gameUserNameArr[(int)(Math.random()*100)%45]);

                TextView gameDes_textView = (TextView) var1.getTextView(R.id.gameDes_textView);
                gameDes_textView.setText("完成任务");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getHeadResId() {
        int headNum = (int)((Math.random()*100)%30);
        String headResString = "gameuserhead"+headNum;
        return  headResString;
    }

    @Override
    public int getItemCount() {
        return 10;
    }
//    public int getItemCount() {
//        return data == null ? 0 : data.size();
//    }

    public void setData(List<TryPlayListResponse.DataBean> data) {
        this.data = data;
    }
}