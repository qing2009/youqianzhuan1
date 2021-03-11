package com.shanqb.wanglezhuan.activity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shanqb.wanglezhuan.R;
import com.shanqb.wanglezhuan.bean.BaseJsonResponse;
import com.shanqb.wanglezhuan.inter.MyQueueResponse;
import com.shanqb.wanglezhuan.utils.AcitonConstants;
import com.shanqb.wanglezhuan.utils.SharedPreConstants;
import com.shanqb.wanglezhuan.utils.SharedPreferencesUtil;
import com.shanqb.wanglezhuan.utils.XToastUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.edittext.ClearEditText;
import com.xuexiang.xui.widget.edittext.PasswordEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class WithdrawActivity extends MyBaseActivity implements MyQueueResponse {
    @BindView(R.id.withdrawalAccount_titlebar)
    TitleBar withdrawalAccountTitlebar;
    @BindView(R.id.tx_amount_clearEditText)
    ClearEditText txAmountClearEditText;
    @BindView(R.id.tx_pwd_clearEditText)
    PasswordEditText txPwdClearEditText;
    @BindView(R.id.tixian_tips_textView)
    TextView tixianTextView;

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
        String tipsString = getString(R.string.tx_tips,SharedPreferencesUtil.getStringValue(this, SharedPreConstants.txMinAmt, ""));
        tixianTextView.setText(tipsString);
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
        Map<String, String> map = new HashMap<String, String>();
        map.put(AcitonConstants.MERCODE, SharedPreferencesUtil.getStringValue(this, SharedPreConstants.merCode, ""));
        map.put(AcitonConstants.TIXIAN_ACCOUNT, amount);
        map.put(AcitonConstants.LOGIN_PASSWORD, pwd);
        requestPostQueue(AcitonConstants.TIXIAN,map,this);
    }

    @Override
    public void onResponse(String response) {
        BaseJsonResponse responseBean = new Gson().fromJson(response, new TypeToken<BaseJsonResponse>() {
        }.getType());
        if (responseBean != null) {
            if (responseBean.isSuccess()) {
                XToastUtils.toast(responseBean.getMessage());
                finish();
            }else {
                XToastUtils.toast(responseBean.getMessage());
            }
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        XToastUtils.toast(error.getMessage());
    }
}
