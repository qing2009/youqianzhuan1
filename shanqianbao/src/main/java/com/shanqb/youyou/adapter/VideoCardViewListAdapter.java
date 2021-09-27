package com.shanqb.youyou.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.shanqb.youyou.R;
import com.shanqb.youyou.bean.video.VideoBean;
import com.shanqb.youyou.utils.Global;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.WidgetUtils;

import java.util.List;

/**
 * @author XUE
 * @since 2019/5/9 10:41
 */
public class VideoCardViewListAdapter extends BaseRecyclerAdapter<VideoBean> {

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.adapter_video_list_item;
    }

    @Override
    public void bindData(@NonNull RecyclerViewHolder holder, int position, VideoBean model) {

        if (model != null) {
            holder.image(R.id.videoImage_imgView, Global.BASE_VIDEO_DOMAIN+model.getImage());
            holder.text(R.id.title_tView, model.getTitle());
            holder.text(R.id.playNums_tView, model.getHits()+"次播放");
            holder.text(R.id.lenght_tView, model.getLengthTime());

            holder.image(R.id.merImg_cirImgView, model.getMer().getMerPhoto());
//            CircleImageView circleImageView = (CircleImageView) holder.findView(R.id.merImg_cirImgView);
//            circleImageView.setImageURI(Uri.parse(model.getMer().getMerPhoto()));
            holder.text(R.id.merName_tView, model.getMer().getMerName());
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads==null || payloads.size()<=0) {
//            Logger.e("正在进行全量刷新:" + position);
            onBindViewHolder(holder, position);
            return;
        }
        // payloads为非空的情况，进行局部刷新

        //取出我们在getChangePayload（）方法返回的bundle
        Bundle payload = WidgetUtils.getChangePayload(payloads);
        if (payload == null) {
            return;
        }

//        Logger.e("正在进行增量刷新:" + position);
//        NewInfo163Item newInfo = getItem(position);
//        for (String key : payload.keySet()) {
//            switch (key) {
//                case DiffUtilCallback.PAYLOAD_USER_NAME:
//                    //这里可以用payload里的数据，不过newInfo也是新的 也可以用
//                    holder.text(R.id.tv_user_name, newInfo.getUserName());
//                    break;
//                case DiffUtilCallback.PAYLOAD_PRAISE:
//                    holder.text(R.id.tv_praise, payload.getInt(DiffUtilCallback.PAYLOAD_PRAISE) == 0 ? "点赞" : String.valueOf(payload.getInt(DiffUtilCallback.PAYLOAD_PRAISE)));
//                    break;
//                case DiffUtilCallback.PAYLOAD_COMMENT:
//                    holder.text(R.id.tv_comment, payload.getInt(DiffUtilCallback.PAYLOAD_COMMENT) == 0 ? "评论" : String.valueOf(payload.getInt(DiffUtilCallback.PAYLOAD_COMMENT)));
//                    break;
//                case DiffUtilCallback.PAYLOAD_READ_NUMBER:
//                    holder.text(R.id.tv_read, "阅读量 " + payload.getInt(DiffUtilCallback.PAYLOAD_READ_NUMBER));
//                    break;
//                default:
//                    break;
//            }
//        }
    }

}
