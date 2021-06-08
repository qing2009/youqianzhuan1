package com.shanqb.demo.tabview;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shanqb.demo.R;
import com.shanqb.demo.bean.BaseJsonResponse2;
import com.shanqb.demo.bean.ContentPage;
import com.shanqb.demo.bean.video.VideoTypeResponse;
import com.shanqb.demo.inter.MyQueueResponse;
import com.shanqb.demo.utils.Global;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 视频资讯
 */
public class VideosFragment extends BaseFragment implements MyQueueResponse {

    @BindView(R.id.tabSegment)
    TabSegment mTabSegment;
    @BindView(R.id.contentViewPager)
    ViewPager mContentViewPager;

    Unbinder unbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabsegment, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }




    @Override
    public void fetchData() {
        requestGetQueue(false, Global.GET_VIDEO_TYPE,this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResponse(String actonString, String response) {
        dismissLoadingDialog();

        if (!TextUtils.isEmpty(response)) {
            VideoTypeResponse videoTypeListResponse = new Gson().fromJson(response, VideoTypeResponse.class);
            if (videoTypeListResponse!=null
                    && BaseJsonResponse2.STATE_SUCCESS.equals(videoTypeListResponse.getState())
                    && videoTypeListResponse.getData()!=null
                    && videoTypeListResponse.getData().size()>0){

                FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

                for (VideoTypeResponse.DataBean dataBean : videoTypeListResponse.getData()) {


                    TabSegment.Tab tab = new TabSegment.Tab(dataBean.getName());
                    tab.setTextColor(getResources().getColor(R.color.textNineColor),getResources().getColor(R.color.custom_color_main_theme));
                    mTabSegment.addTab(tab);

                    adapter.addFragment(VideoSimpleListFragment.getInstance(dataBean.getId()),dataBean.getName());
                }

                mContentViewPager.setAdapter(adapter);
                mContentViewPager.setCurrentItem(0, false);

                int space = DensityUtils.dp2px(getContext(), 16);
                mTabSegment.setHasIndicator(true);
                mTabSegment.setMode(TabSegment.MODE_SCROLLABLE);
                mTabSegment.setItemSpaceInScrollMode(space);
                mTabSegment.setupWithViewPager(mContentViewPager, false);
                mTabSegment.setPadding(space, 0, space, 0);

            }
        }

    }

    @Override
    public void onErrorResponse(String actonString, VolleyError error) {
    }
}
