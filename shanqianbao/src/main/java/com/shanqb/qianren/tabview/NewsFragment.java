package com.shanqb.qianren.tabview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.shanqb.qianren.bean.ContentPage;
import com.shanqb.qianren.R;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewsFragment extends BaseFragment {

    @BindView(R.id.tabSegment)
    TabSegment mTabSegment;
    @BindView(R.id.contentViewPager)
    ViewPager mContentViewPager;

    Unbinder unbinder;
    String[] pages = ContentPage.getPageNames();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabsegment, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews();
        return view;
    }

    protected void initViews() {
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());


        ContentPage[] pages = ContentPage.values();
        for (ContentPage page:pages) {
            String requestUrl = getString(R.string.news_url_163_toutiao);
            if (ContentPage.头条.equals(page)){
                requestUrl = getString(R.string.news_url_163_toutiao);

            }else if (ContentPage.汽车.equals(page)){
                requestUrl = getString(R.string.news_url_163_car);

            }else if (ContentPage.娱乐.equals(page)){
                requestUrl = getString(R.string.news_url_163_yule);

            }else if (ContentPage.体育.equals(page)) {
                requestUrl = getString(R.string.news_url_163_tiyu);

            }else if (ContentPage.运动.equals(page)) {
                requestUrl = getString(R.string.news_url_163_yundong);

            }


            TabSegment.Tab tab = new TabSegment.Tab(page.name());
            tab.setTextColor(getResources().getColor(R.color.textNineColor),getResources().getColor(R.color.custom_color_main_theme));

            mTabSegment.addTab(tab);
            adapter.addFragment(SimpleListFragment.getInstance(requestUrl,page.name()), page.name());
        }
//        for (String page : pages) {
//            mTabSegment.addTab(new TabSegment.Tab(page));
////            adapter.addFragment(new SimpleListFragment(), page);
//            String requestUrl = getString(R.string.news_url_163_toutiao);
//            adapter.addFragment(SimpleListFragment.getInstance(requestUrl), page);
//
//        }
        mContentViewPager.setAdapter(adapter);
        mContentViewPager.setCurrentItem(0, false);
        mTabSegment.setupWithViewPager(mContentViewPager, false);
        mTabSegment.setMode(TabSegment.MODE_FIXED);

    }



    @Override
    public void fetchData() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
