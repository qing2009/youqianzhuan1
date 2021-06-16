package com.shanqb.demo.tabview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @OnClick({R.id.home_mahua, R.id.home_xiaoshuo, R.id.home_xinwen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_mahua:
                Utils.goWeb(getContext(), "https://m.kuaikanmanhua.com/");
                break;
            case R.id.home_xiaoshuo:
                SharedPreferencesUtil.clear(getActivity());

                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.home_xinwen:
                startActivity(new Intent(getActivity(), TryPlayRecordActivity.class));
                break;
        }
    }

//    @OnClick({R.id.home_mahua, R.id.home_xiaoshuo, R.id.home_xinwen})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.home_mahua:
//                break;
//            case R.id.home_xiaoshuo:
//                break;
//            case R.id.home_xinwen:
//                break;
//        }
//    }
}
