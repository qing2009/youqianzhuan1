package com.shanqb.weishouzhuan.adapter;

import android.content.Context;
import android.widget.TextView;

import com.shanqb.weishouzhuan.R;
import com.shanqb.weishouzhuan.bean.TryPlayListResponse;
import com.shanqb.weishouzhuan.bean.ZhuanjinTop;
import com.shanqb.weishouzhuan.test.BaseRecyclerViewAdapter;
import com.shanqb.weishouzhuan.view.CircleImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeTopListAdapter extends BaseRecyclerViewAdapter {

    List<ZhuanjinTop> data = new ArrayList<>();
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

        for (int i=0; i<10;i++) {
            ZhuanjinTop zhuanjinTop = new ZhuanjinTop();
            zhuanjinTop.setUserHeadResId(gameHeadArr[(int)(Math.random()*100)%29]);
            zhuanjinTop.setGameName(gameUserNameArr[(int)(Math.random()*100)%45]);
            zhuanjinTop.setGameDes("完成任务");

            double moneyDouble = Math.random();
            if (moneyDouble<0.1){
                moneyDouble = 0.1;
            }
            moneyDouble = moneyDouble*500;
            DecimalFormat df = new DecimalFormat("#.##");
            Double get_double = Double.parseDouble(df.format(moneyDouble));
            zhuanjinTop.setMoney(get_double);

            data.add(zhuanjinTop);
        }

        Collections.sort(data,new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof ZhuanjinTop && o2 instanceof ZhuanjinTop){
                    ZhuanjinTop e1 = (ZhuanjinTop) o1;
                    ZhuanjinTop e2 = (ZhuanjinTop) o2;

                    return Double.compare(e2.getMoney() , e1.getMoney());
                }
                throw new ClassCastException("不能转换为Emp类型");
            }
        });

    }

    @Override
    public void onBindViewData(ViewHolder var1, int var2) {
        try {
            ZhuanjinTop zhuanjinTop = data.get(var2);
//            TryPlayListResponse.DataBean bean = data.get(var2);
//            if (bean != null) {
                TextView income_textView = (TextView) var1.getTextView(R.id.income_textView);
                income_textView.setText("¥" +zhuanjinTop.getMoney()+ "元");

                CircleImageView gameHeadImgView = (CircleImageView) var1.getImageView(R.id.headImageView);
                gameHeadImgView.setImageResource(zhuanjinTop.getUserHeadResId());

                TextView gameName_textView = (TextView) var1.getTextView(R.id.gameName_textView);
                gameName_textView.setText(zhuanjinTop.getGameDes());

                TextView gameDes_textView = (TextView) var1.getTextView(R.id.gameDes_textView);
                gameDes_textView.setText(zhuanjinTop.getGameDes());
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

//    public void setData(List<TryPlayListResponse.DataBean> data) {
//        this.data = data;
//    }
}