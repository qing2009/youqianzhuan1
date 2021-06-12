package com.shanqb.mayi.activity;

import android.widget.TextView;

import com.shanqb.mayi.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ReadGetMoneyActivity extends MyBaseActivity {
    @BindView(R.id.title_textView)
    TextView titleTextView;

    @Override
    public void initLayout() {
        setContentView(R.layout.activity_readgetmoney);
    }

    @Override
    public void initBundleData() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initWeight() {
        titleTextView.setText(getString(R.string.read_get_money));
    }

    @OnClick(R.id.back_imgView)
    public void onViewClicked() {
        onBackPressed();
    }
}
