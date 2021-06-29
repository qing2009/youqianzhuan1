package com.shanqb.demo.tabview;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.shanqb.demo.R;
import com.shanqb.demo.activity.LoginActivity;
import com.shanqb.demo.activity.TryPlayRecordActivity;
import com.shanqb.demo.utils.DeviceUtils;
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
        String aid = Settings.System.getString(getContext().getContentResolver(), Settings.System.ANDROID_ID);
        Toast.makeText(getActivity(),"Device="+DeviceUtils.getDeviceId(getContext())+"imei="+DeviceUtils.getIMEI(getContext(),0)+"android="+aid,Toast.LENGTH_LONG).show();
//
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

    @OnClick({R.id.game_lvzitiaotiao, R.id.game_splash, R.id.game_hextris,R.id.game_kuaipao, R.id.game_chinesechess, R.id.game_zipai, R.id.home_kuaizhuan, R.id.home_tuijian,R.id.suansu, R.id.fangkuai, R.id.jump, R.id.daoguo,R.id.qmxzfzm, R.id.jianren, R.id.semo, R.id.ygj})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.game_lvzitiaotiao:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/lvzitiaotiao");
                break;
            case R.id.game_splash:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/splash");
                break;
            case R.id.game_hextris:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/hextris");
                break;
            case R.id.game_kuaipao:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/xiaoniaofeifei");
                break;
            case R.id.game_chinesechess:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/Chinesechess");
                break;
            case R.id.game_zipai:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/zipai");
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
                Utils.goWeb(getContext(), "http://8.133.178.205/game/semo/");
                break;
            case R.id.ygj:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/ygj/");
                break;
        }
    }

}
