package com.shanqb.myTool.tabview;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shanqb.myTool.R;
import com.shanqb.myTool.adapter.VideoCardViewListAdapter;
import com.shanqb.myTool.bean.BaseJsonResponse2;
import com.shanqb.myTool.bean.video.VideoBean;
import com.shanqb.myTool.bean.video.VideoListResponse;
import com.shanqb.myTool.inter.MyQueueResponse;
import com.shanqb.myTool.utils.Global;
import com.shanqb.myTool.utils.Utils;
import com.xuexiang.xui.adapter.recyclerview.DividerItemDecoration;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author XUE
 * @since 2019/5/9 11:54
 */
public class VideoSimpleListFragment extends BaseFragment implements MyQueueResponse {

    private final String TAG = "VideoSimpleListFragment";

    Unbinder unbinder;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private VideoCardViewListAdapter mAdapter;
    private String typeId;
    private int page = 1;
    private int size = 5;


    public static VideoSimpleListFragment getInstance(String typeId){
        VideoSimpleListFragment simpleListFragment = new VideoSimpleListFragment();
        Bundle b = new Bundle();
        b.putString("typeId",typeId);
        simpleListFragment.setArguments(b);
        return simpleListFragment;
    }



    protected void initViews() {
        Log.d(TAG, "initViews() called");
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL,5));
        recyclerView.setAdapter(mAdapter = new VideoCardViewListAdapter());

        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        mAdapter.setOnItemClickListener(new RecyclerViewHolder.OnItemClickListener<VideoBean>() {
            @Override
            public void onItemClick(View itemView, VideoBean item, int position) {
                Utils.goWeb(getContext(),Global.BASE_VIDEO_DOMAIN+item.getFilePath());
            }
        });

        String requestUrl = Global.GET_VIDEO_LIST+"type="+typeId+"&page="+page+"&size="+size;
        requestGetQueue(true,requestUrl,this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        View view = inflater.inflate(R.layout.include_recycler_view_refresh, container, false);
        unbinder = ButterKnife.bind(this, view);

        typeId = getArguments().getString("typeId");

        initViews();
        return view;
    }


    @Override
    public void fetchData() {
        Log.d(TAG, "fetchData() called");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onResponse(String actonString, String response) {
        Log.d(TAG, "onResponse() called with: actonString = [" + actonString + "], response = [" + response + "]");
        dismissLoadingDialog();

        if (!TextUtils.isEmpty(response)) {
            VideoListResponse videoListResponse = new Gson().fromJson(response, VideoListResponse.class);
            if (videoListResponse != null
                    && BaseJsonResponse2.STATE_SUCCESS.equals(videoListResponse.getState())
                    && videoListResponse.getData() != null
                    && videoListResponse.getData().size() > 0) {

                mAdapter.refresh(videoListResponse.getData());
            }
        }
    }


    @Override
    public void onErrorResponse(String actonString, VolleyError error) {
        dismissLoadingDialog();
        Log.d(TAG, "onErrorResponse() called with: actonString = [" + actonString + "], error = [" + error + "]");
    }
}
