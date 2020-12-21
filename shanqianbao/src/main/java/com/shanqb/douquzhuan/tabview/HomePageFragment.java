package com.shanqb.douquzhuan.tabview;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallback;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.shanqb.douquzhuan.R;
import com.shanqb.douquzhuan.activity.ReadGetMoneyActivity;
import com.shanqb.douquzhuan.utils.SharedPreConstants;
import com.shanqb.douquzhuan.utils.SharedPreferencesUtil;
import com.shanqb.douquzhuan.utils.sdk.JuxiangyouUtils;
import com.shanqb.douquzhuan.utils.sdk.Taojing91Utils;
import com.shanqb.douquzhuan.view.CircleImageView;
import com.shanqb.douquzhuan.utils.sdk.XianWangUtils;

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



    @OnClick({R.id.taskGetMoney_imgView, R.id.readGetMoney_imgView, R.id.juxiangwang_btn, R.id.taojing91_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.taskGetMoney_imgView:
                PermissionX.init(this)
                        .permissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE)
                        .onExplainRequestReason(new ExplainReasonCallback() {
                            @Override
                            public void onExplainReason(ExplainScope scope, List<String> deniedList) {
                                scope.showRequestReasonDialog(deniedList, getString(R.string.need_agree_permissions), getString(R.string.agree), getString(R.string.cancel));
                            }
                        })
                        .onForwardToSettings(new ForwardToSettingsCallback() {
                            @Override
                            public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
                                scope.showForwardToSettingsDialog(deniedList, getString(R.string.to_set_open_permissions), getString(R.string.openSet), getString(R.string.cancel));
                            }
                        })
                        .request(new RequestCallback() {
                            @Override
                            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                                if (allGranted) {
//                                    Toast.makeText(getActivity(), "All permissions are granted", Toast.LENGTH_LONG).show();
//                                    textview.setText("deviceId=" + deviceId + "; xwdeviceid=" + xwdeviceid);

                                    XianWangUtils.startSDK(getActivity());
                                } else {
//                                    Toast.makeText(getActivity(), "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                break;


            case R.id.juxiangwang_btn:

//                PermissionX.init(this)
//                        .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE)
//                        .onExplainRequestReason(new ExplainReasonCallback() {
//                            @Override
//                            public void onExplainReason(ExplainScope scope, List<String> deniedList) {
//                                scope.showRequestReasonDialog(deniedList, getString(R.string.need_agree_permissions), getString(R.string.agree), getString(R.string.cancel));
//                            }
//                        })
//                        .onForwardToSettings(new ForwardToSettingsCallback() {
//                            @Override
//                            public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
//                                scope.showForwardToSettingsDialog(deniedList, getString(R.string.to_set_open_permissions), getString(R.string.openSet), getString(R.string.cancel));
//                            }
//                        })
//                        .request(new RequestCallback() {
//                            @Override
//                            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
//                                if (allGranted) {
//                                    String userId = "654321";
//                                    String merCode = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.merCode, "");
//                                    JuxiangyouUtils.startSDK(getActivity(),userId);
//                                } else {
////                                    Toast.makeText(getActivity(), "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
//                break;
            case R.id.taojing91_btn:
                String userId=(int)(Math.random()*1000000000)+"";
                Taojing91Utils.startSDK(getActivity(),userId);
                break;
            case R.id.readGetMoney_imgView:
                startActivity(new Intent(getActivity(), ReadGetMoneyActivity.class));
                break;
        }
    }

}
