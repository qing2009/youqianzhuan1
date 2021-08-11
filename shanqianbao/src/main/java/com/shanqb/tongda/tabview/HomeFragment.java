package com.shanqb.tongda.tabview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.shanqb.tongda.R;
import com.shanqb.tongda.adapter.RecyclerViewBannerAdapter2;
import com.shanqb.tongda.utils.DemoDataProvider;
import com.shanqb.tongda.utils.Utils;
import com.shanqb.tongda.view.RoundCornerImageView;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yx on 16/4/3.
 */
public class HomeFragment extends BaseFragment implements ITabClickListener, BannerLayout.OnBannerItemClickListener {

    Unbinder unbinder;

//    @BindView(R.id.taskGetMoney_imgView)
//    RoundCornerImageView taskGetMoneyImgView;
    @BindView(R.id.bl_horizontal)
    BannerLayout blHorizontal;//轮播图

    private RecyclerViewBannerAdapter2 mAdapterHorizontal;//轮播图

    private ViewPager mViewPager;

    @Override
    public void fetchData() {

        setShouyiView();
    }

    /**
     * 显示收益
     */
    private void setShouyiView() {
        Log.e("ProfileFragment", "setShouyiView() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewPager = getActivity().findViewById(R.id.viewpager);
        //刷新总收益、可提现
        setShouyiView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        //轮播图
        blHorizontal.setAdapter(mAdapterHorizontal = new RecyclerViewBannerAdapter2(DemoDataProvider.urls));
        mAdapterHorizontal.setOnBannerItemClickListener(this);

        return view;
    }

    @Override
    public void onMenuItemClick() {

    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.home_mahua, R.id.home_xiaoshuo, R.id.home_xinwen, R.id.home_kuaizhuan, R.id.home_tuijian, R.id.image1, R.id.image2, R.id.image3, R.id.image4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_mahua:
                Utils.goWeb(getContext(), "http://www.chinjgeriatr.com/m/index.html");
                break;
            case R.id.home_xiaoshuo:
                Utils.goWeb(getContext(), "https://3g.ali213.net/news/");
                break;
            case R.id.home_xinwen:
                Utils.goWeb(getContext(), "http://m.yxdown.com/news/");
                break;
            case R.id.home_kuaizhuan:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.home_tuijian:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.image1:
                Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                break;
            case R.id.image2:
                Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                break;
            case R.id.image3:
                Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                break;
            case R.id.image4:
                Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemClick(int position) {

    }
}
