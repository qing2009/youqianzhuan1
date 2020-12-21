package com.shanqb.douquzhuan.activity;

import android.view.View;
import android.widget.Toast;

import com.shanqb.douquzhuan.R;
import com.shanqb.douquzhuan.utils.NetworkUtils;
import com.shanqb.douquzhuan.utils.XToastUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.edittext.ClearEditText;
import com.xuexiang.xui.widget.edittext.PasswordEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class WithdrawActivity extends MyBaseActivity {
    @BindView(R.id.withdrawalAccount_titlebar)
    TitleBar withdrawalAccountTitlebar;
    @BindView(R.id.tx_amount_clearEditText)
    ClearEditText txAmountClearEditText;
    @BindView(R.id.tx_pwd_clearEditText)
    PasswordEditText txPwdClearEditText;

    @Override
    public void initLayout() {
        setContentView(R.layout.activity_withdraw);
    }

    @Override
    public void initBundleData() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initWeight() {
        withdrawalAccountTitlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @OnClick(R.id.tx_submit_btn)
    public void onClick() {
        if (isTXAmountAndPwdValid()) {
            txRequest(txAmountClearEditText.getText().toString(), txPwdClearEditText.getText().toString());
        }
    }


    public boolean isTXAmountAndPwdValid() {
        if (txAmountClearEditText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.tx_amount_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (txPwdClearEditText.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.tx_pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void txRequest(String amount, String pwd) {
        try {
            if (!NetworkUtils.checkNetworkConnectionState(this)) {//未连接到网络
                XToastUtils.toast(getString(R.string.net_close));
                return;
            }

            XToastUtils.toast(getString(R.string.submit_success));
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            dismissLoadingDialog();
        }
    }
}
