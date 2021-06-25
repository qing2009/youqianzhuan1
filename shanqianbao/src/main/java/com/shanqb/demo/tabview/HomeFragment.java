package com.shanqb.demo.tabview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.shanqb.demo.R;
import com.shanqb.demo.activity.LoginActivity;
import com.shanqb.demo.activity.TryPlayRecordActivity;
import com.shanqb.demo.utils.SharedPreferencesUtil;
import com.shanqb.demo.utils.Utils;
import com.shanqb.demo.view.RoundCornerImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yx on 16/4/3.
 */
public class HomeFragment extends BaseFragment implements ITabClickListener {

    Unbinder unbinder;

    @BindView(R.id.taskGetMoney_imgView)
    RoundCornerImageView taskGetMoneyImgView;
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
                Utils.goWeb(getContext(), "https://m.kuaikanmanhua.com/");
                break;
            case R.id.home_xiaoshuo:
                Utils.goWeb(getContext(), "https://m.readnovel.com/");
                break;
            case R.id.home_xinwen:
                Utils.goWeb(getContext(), "https://3g.163.com/?ver=c");
                break;
            case R.id.home_kuaizhuan:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.home_tuijian:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.suansu:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/123suansu/");
                break;
            case R.id.fangkuai:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/fangkuai/");
                break;
            case R.id.jump:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/jump/");
                break;
            case R.id.daoguo:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/daoguo/");
                break;
            case R.id.qmxzfzm:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/qmxzfzm/");
                break;
            case R.id.jianren:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/jianren/");
                break;
            case R.id.semo:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/jump/");
                break;
            case R.id.ygj:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/daoguo/");
                break;
        }
    }

}
