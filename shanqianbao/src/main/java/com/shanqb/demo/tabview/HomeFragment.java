package com.shanqb.demo.tabview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shanqb.demo.R;
import com.shanqb.demo.activity.LoginActivity;
import com.shanqb.demo.activity.ResetpwdActivity;
import com.shanqb.demo.activity.TryPlayRecordActivity;
import com.shanqb.demo.activity.WithdrawActivity;
import com.shanqb.demo.activity.WithdrawalAccountActivity;
import com.shanqb.demo.utils.SharedPreConstants;
import com.shanqb.demo.utils.SharedPreferencesUtil;
import com.shanqb.demo.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yx on 16/4/3.
 */
public class HomeFragment extends BaseFragment implements ITabClickListener {

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

    @OnClick({R.id.withdrawDeposit_textView, R.id.changePwd_textView, R.id.logout_textView, R.id.tryPlay_textView, R.id.withdraw_textView, R.id.withdrawalAccount_textView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.withdrawDeposit_textView:
                break;
            case R.id.changePwd_textView:
                startActivity(new Intent(getActivity(), ResetpwdActivity.class));
                break;
            case R.id.logout_textView:
                SharedPreferencesUtil.clear(getActivity());

                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.tryPlay_textView:
                startActivity(new Intent(getActivity(), TryPlayRecordActivity.class));
                break;
            case R.id.withdraw_textView:
                startActivity(new Intent(getActivity(), WithdrawActivity.class));
                break;
            case R.id.withdrawalAccount_textView:
                startActivity(new Intent(getActivity(), WithdrawalAccountActivity.class));
                break;
        }
    }
}
