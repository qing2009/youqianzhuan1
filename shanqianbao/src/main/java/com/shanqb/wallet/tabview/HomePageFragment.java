package com.shanqb.wallet.tabview;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pceggs.workwall.util.PceggsWallUtils;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.shanqb.wallet.R;
import com.shanqb.wallet.activity.ReadGetMoneyActivity;
import com.shanqb.wallet.activity.TryPlayRecordActivity;
import com.shanqb.wallet.utils.SharedPreConstants;
import com.shanqb.wallet.utils.SharedPreferencesUtil;
import com.shanqb.wallet.view.CircleImageView;
import com.shanqb.wallet.xianwang.XianWangUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by yx on 16/4/3.
 */
public class HomePageFragment extends BaseFragment implements ITabClickListener {
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
    Unbinder unbinder;

    String deviceId = null;
    String xwdeviceid = null;

    @Override
    public void fetchData() {
        userNameTextView.setText(SharedPreferencesUtil.getStringValue(getActivity(),SharedPreConstants.loginCode,""));
        String totalRevenue = SharedPreferencesUtil.getStringValue(getActivity(),SharedPreConstants.allAmt,"0.00");
        totalRevenueTextView.setText(totalRevenue);
        String withdrawable = SharedPreferencesUtil.getStringValue(getActivity(),SharedPreConstants.txAmt,"0.00");
        withdrawableTextView.setText(withdrawable);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_layout, container, false);
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



    @OnClick({R.id.taskGetMoney_imgView, R.id.readGetMoney_imgView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.taskGetMoney_imgView:
                PermissionX.init(this)
                        .permissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE)
                        .request(new RequestCallback() {
                            @Override
                            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                                if (allGranted) {
//                                    Toast.makeText(getActivity(), "All permissions are granted", Toast.LENGTH_LONG).show();

                                    deviceId = XianWangUtils.getDeviceId(getActivity());
                                    xwdeviceid = XianWangUtils.getXwdeviceid(getActivity());
                                    String merCode = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.merCode, "");

//                            textview.setText("deviceId=" + deviceId + "; xwdeviceid=" + xwdeviceid);
                                    PceggsWallUtils.loadAd(getActivity(), XianWangUtils.XIANWANG_PID, XianWangUtils.XIANWANG_APPKEY, merCode, deviceId, xwdeviceid);
//                                PceggsWallUtils.loadAd(getActivity(), "10001", "PCDDXW_CS_10001", userid + "", deviceId, xwdeviceid);

                                } else {
//                                    Toast.makeText(getActivity(), "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                break;
            case R.id.readGetMoney_imgView:
                startActivity(new Intent(getActivity(), ReadGetMoneyActivity.class));
                break;
        }
    }

}
