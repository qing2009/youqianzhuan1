package com.shanqb.shandianli.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.shanqb.shandianli.R;
import com.shanqb.shandianli.bean.NewInfo163Item;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.WidgetUtils;

import java.util.List;

/**
 * @author XUE
 * @since 2019/5/9 10:41
 */
public class NewsCardViewListAdapter extends BaseRecyclerAdapter<NewInfo163Item> {

    @Override
    public int getItemLayoutId(int viewType) {
//        return R.layout.adapter_news_card_view_list_item;
        return R.layout.adapter_news_list_item;
    }

    @Override
    public void bindData(@NonNull RecyclerViewHolder holder, int position, NewInfo163Item model) {

        if (model != null) {
            holder.text(R.id.tv_user_name, model.getSource());
//            holder.text(R.id.tv_tag, model.getTag());
            holder.text(R.id.tv_title, model.getTitle());
            holder.text(R.id.tv_summary, model.getDigest());
            holder.text(R.id.tv_praise, model.getReplyCount()+"");
            holder.text(R.id.tv_comment, model.getVotecount()+"");
            holder.text(R.id.tv_read, "阅读量 " + model.getDaynum());
            holder.image(R.id.iv_image, model.getImgsrc());
//            holder.text(R.id.tv_user_name, model.getUserName());
//            holder.text(R.id.tv_tag, model.getTag());
//            holder.text(R.id.tv_title, model.getTitle());
//            holder.text(R.id.tv_summary, model.getSummary());
//            holder.text(R.id.tv_praise, model.getPraise() == 0 ? "点赞" : String.valueOf(model.getPraise()));
//            holder.text(R.id.tv_comment, model.getComment() == 0 ? "评论" : String.valueOf(model.getComment()));
//            holder.text(R.id.tv_read, "阅读量 " + model.getRead());
//            holder.image(R.id.iv_image, model.getImageUrl());
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
        NewInfo163Item newInfo = getItem(position);
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