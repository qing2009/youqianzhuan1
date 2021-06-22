package com.shanqb.shandianli.tabview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shanqb.shandianli.R;
import com.shanqb.shandianli.activity.LoginActivity;
import com.shanqb.shandianli.activity.ResetpwdActivity;
import com.shanqb.shandianli.activity.TryPlayRecordActivity;
import com.shanqb.shandianli.activity.WithdrawActivity;
import com.shanqb.shandianli.utils.SharedPreConstants;
import com.shanqb.shandianli.utils.SharedPreferencesUtil;
import com.shanqb.shandianli.view.CircleImageView;
import com.shanqb.shandianli.activity.WithdrawalAccountActivity;

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
    @BindView(R.id.yaoqingmaCode_textView)
    TextView youqingmaSuperText;
    @BindView(R.id.userInfo_yaoqingma_textView)
    TextView userYouqingmaSuperText;

    @Override
    public void fetchData() {
        userNameTextView.setText(SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.loginCode, ""));
        String yaoqingma = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.shareCode, "");
        youqingmaSuperText.setText(yaoqingma);
        userYouqingmaSuperText.setText(getString(R.string.yaoqingma)+": "+yaoqingma);

        setShouyiView();
    }

    /**
     * 显示收益
     */
    private void setShouyiView() {
        Log.e("ProfileFragment", "setShouyiView() called");
        String totalRevenue = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.allAmt, "0.00");
        totalRevenueTextView.setText(totalRevenue);
        String withdrawable = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.txAmt, "0.00");
        withdrawableTextView.setText(withdrawable);
    }

    @Override
    public void onResume() {
        super.onResume();

        //刷新总收益、可提现
        setShouyiView();
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

    @OnClick({R.id.image4,R.id.withdrawDeposit_textView, R.id.changePwd_textView, R.id.logout_textView, R.id.tryPlay_textView, R.id.withdraw_textView, R.id.withdrawalAccount_textView})
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
            case R.id.image1:
                startActivity(new Intent(getActivity(), TryPlayRecordActivity.class));
                break;
            case R.id.withdraw_textView:
                startActivity(new Intent(getActivity(), WithdrawActivity.class));
                break;
            case R.id.image3:
                startActivity(new Intent(getActivity(), WithdrawalAccountActivity.class));
                break;
            case R.id.image4:
                String loginCode = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.loginCode, "");
                Toast.makeText(getActivity(),"您绑定的手机号："+loginCode,Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
