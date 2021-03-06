package com.shanqb.douquzhuan.tabview;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shanqb.douquzhuan.R;
import com.shanqb.douquzhuan.activity.LoginActivity;
import com.shanqb.douquzhuan.activity.ResetpwdActivity;
import com.shanqb.douquzhuan.activity.TryPlayRecordActivity;
import com.shanqb.douquzhuan.activity.WithdrawActivity;
import com.shanqb.douquzhuan.utils.SharedPreConstants;
import com.shanqb.douquzhuan.utils.SharedPreferencesUtil;
import com.shanqb.douquzhuan.view.CircleImageView;
import com.shanqb.douquzhuan.activity.WithdrawalAccountActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yx on 16/4/3.
 */
public class ProfileFragment extends BaseFragment implements ITabClickListener {
    @BindView(R.id.headImageView)
    CircleImageView headImageView;
    @BindView(R.id.userNameTextView)
    TextView userNameTextView;
    @BindView(R.id.phoneTextView)
    TextView phoneTextView;
    @BindView(R.id.totalRevenueTextView)
    TextView totalRevenueTextView;
    @BindView(R.id.withdrawableTextView)
    TextView withdrawableTextView;
    @BindView(R.id.withdrawDeposit_textView)
    TextView withdrawDepositTextView;
    @BindView(R.id.changePwd_textView)
    TextView changePwdTextView;
    @BindView(R.id.logout_textView)
    TextView logoutTextView;
    Unbinder unbinder;
    @BindView(R.id.withdrawalAccount_textView)
    TextView withdrawalAccountTextView;

    @Override
    public void fetchData() {
        userNameTextView.setText(SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.loginCode, ""));
        String totalRevenue = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.allAmt, "0.00");
        totalRevenueTextView.setText(totalRevenue);
        String withdrawable = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.txAmt, "0.00");
        withdrawableTextView.setText(withdrawable);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_layout, container, false);
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
        unbinder.unbind();
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
