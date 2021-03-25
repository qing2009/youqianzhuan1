package com.shanqb.ttaiyou.tabview;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duoyou.task.openapi.DyAdApi;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallback;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.shanqb.shanqianbao.MainActivity;
import com.shanqb.ttaiyou.R;
import com.shanqb.ttaiyou.activity.ReadGetMoneyActivity;
import com.shanqb.ttaiyou.adapter.HomeTopListAdapter;
import com.shanqb.ttaiyou.adapter.RecyclerViewBannerAdapter;
import com.shanqb.ttaiyou.utils.DemoDataProvider;
import com.shanqb.ttaiyou.utils.DeviceUtils;
import com.shanqb.ttaiyou.utils.SharedPreConstants;
import com.shanqb.ttaiyou.utils.SharedPreferencesUtil;
import com.shanqb.ttaiyou.utils.sdk.AibianxianUtils;
import com.shanqb.ttaiyou.utils.sdk.JuxiangyouUtils;
import com.shanqb.ttaiyou.utils.sdk.Taojing91Utils;
import com.shanqb.ttaiyou.utils.sdk.XianWangUtils;
import com.shanqb.ttaiyou.utils.sdk.XiquUtils;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jfq.wowan.com.myapplication.PlayMeUtil;


/**
 * Created by yx on 16/4/3.
 */
public class HomePageFragment extends BaseFragment implements ITabClickListener, BannerLayout.OnBannerItemClickListener {
    @BindView(R.id.bl_horizontal)
    BannerLayout blHorizontal;
    //    @BindView(R.id.headImageView)
//    CircleImageView headImageView;
//    @BindView(R.id.userNameTextView)
//    TextView userNameTextView;
//    @BindView(R.id.phoneTextView)
//    TextView phoneTextView;
//    @BindView(R.id.totalRevenueTextView)
//    TextView totalRevenueTextView;
//    @BindView(R.id.withdrawableTextView)
//    TextView withdrawableTextView;
    Unbinder unbinder;
    String merCode;
    @BindView(R.id.taojing91_btn)
    ImageView taojing91Btn;
    @BindView(R.id.aibianxian_btn)
    ImageView aibianxianBtn;
    @BindView(R.id.juxiangwang_btn)
    ImageView juxiangwangBtn;
    @BindView(R.id.lin2Img2_imgView)
    ImageView lin2Img2ImgView;
    @BindView(R.id.lin3Img1_imgView)
    ImageView lin3Img1ImgView;
    @BindView(R.id.lin3Img2_imgView)
    ImageView lin3Img2ImgView;

    @BindView(R.id.recordRecyView)
    RecyclerView recordRecyView;

    private RecyclerViewBannerAdapter mAdapterHorizontal;

    private LinearLayoutManager layoutManager;
    private HomeTopListAdapter adapter;

    @Override
    public void fetchData() {
        try {
//            userNameTextView.setText(SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.loginCode, ""));
//            String totalRevenue = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.allAmt, "0.00");
//            totalRevenueTextView.setText(totalRevenue);
//            String withdrawable = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.txAmt, "0.00");
//            withdrawableTextView.setText(withdrawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        merCode = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.merCode, "");


        int screenWidth = DensityUtils.getScreenMetrics(true).widthPixels;//屏幕宽度
        int spacingWidth = DensityUtils.dp2px(50);//图片控件左右中间使用的空白间距宽度
        int viewWidth = (screenWidth - spacingWidth) / 3;//总共两张图，每张图的宽度。
        int viewHeight = viewWidth * 675 / 1080;//640*280是图片分辨率

//        Log.e(getTag(), "viewWidth: " + viewWidth);
//        Log.e(getTag(), "viewHeight: " + viewHeight);

//        taojing91Btn.getLayoutParams().height = viewHeight;
//        aibianxianBtn.getLayoutParams().height = viewHeight;
//        juxiangwangBtn.getLayoutParams().height = viewHeight;
//        aibianxianBtn.getLayoutParams().height = viewHeight;
//        lin2Img2ImgView.getLayoutParams().height = viewHeight;
//        lin3Img1ImgView.getLayoutParams().height = viewHeight;
//        lin3Img2ImgView.getLayoutParams().height = viewHeight;


        blHorizontal.setAdapter(mAdapterHorizontal = new RecyclerViewBannerAdapter(DemoDataProvider.urls));
        mAdapterHorizontal.setOnBannerItemClickListener(this);

        this.layoutManager = new LinearLayoutManager(getActivity());
        this.layoutManager.setOrientation(1);
        this.recordRecyView.setLayoutManager(this.layoutManager);
        adapter = new HomeTopListAdapter(getActivity());
//        initTop10Data(adapter);
        recordRecyView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onMenuItemClick() {

    }
    public void initTop10Data() {

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


    @OnClick({R.id.taskGetMoney_imgView, R.id.readGetMoney_imgView, R.id.juxiangwang_btn, R.id.taojing91_btn, R.id.aibianxian_btn,R.id.xiqu_btn,R.id.wowang_btn})
    public void onViewClicked(View view) {

        String merCode = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.merCode, "");

        switch (view.getId()) {
            case R.id.taskGetMoney_imgView:
                requestPermission(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                            XianWangUtils.startSDK(getActivity());
                        }
                    }
                });
                break;


            case R.id.juxiangwang_btn:
                requestPermission(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
//                            JuxiangyouUtils.startSDK(getActivity(), merCode);

                            DyAdApi.getDyAdApi().init(getActivity(), "dy_59635174", "8f6e7ad15edefc36ea7650a211f21562","");
//                            DyAdApi.getDyAdApi().init(getActivity(),"dy_59633678", "ee0a8ee5de2ce442c8b094410440ec8c", "channel");//测试id
                            /**
                             * userId : 开发者APP用户标识，代表一个用户的Id，保证唯一性
                             * advertType: 0（默认值）显示全部数据  1.手游  2.棋牌游戏
                             */
                            DyAdApi.getDyAdApi().jumpAdList(getActivity(), merCode, 0);
                        }
                    }
                });
                break;

            case R.id.taojing91_btn:
                requestPermission(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                            Taojing91Utils.startSDK(getActivity(), merCode);
                        }
                    }
                });

                break;
            case R.id.aibianxian_btn:
                requestPermission(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
//                            AibianxianUtils.startSDK(getActivity().getApplication(), merCode, getActivity());
                            Taojing91Utils.startSDK(getActivity(), merCode);
                        }
                    }
                });
                break;
            case R.id.readGetMoney_imgView:
                startActivity(new Intent(getActivity(), ReadGetMoneyActivity.class));
                break;
            case R.id.xiqu_btn:

                requestPermission(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                            XiquUtils.startSDK(getActivity(), merCode);
                        }
                    }
                });
                break;
            case R.id.wowang_btn:
                requestPermission(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                            String deviceId = DeviceUtils.getDeviceId(getActivity());
                            String oaid = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.OAID,"");
                            PlayMeUtil.openIndex(getActivity(),"6968",merCode,deviceId,oaid,"z7ugkaIgSvvvaTg0jFmeBdHDE2p15uHh");
//                            PlayMeUtil.openIndex(getActivity(), "3888", "1443910", deviceId, "dewfew-fregf-gfreg-gre", "Ax5xVDDx9NGbIhefGzqf9S8pT7aM8E72");
                        }
                    }
                });

                break;
        }
    }

    private void requestPermission(RequestCallback requestCallback){
        PermissionX.init(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
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
                .request(requestCallback);
    }

    private void test() {
        Log.e(getClass().getName(), "test: DeviceId=" + DeviceUtils.getDeviceId(getContext()));
        Log.e(getClass().getName(), "test: IMEI 0 =" + DeviceUtils.getIMEI(getContext(), 0));
        Log.e(getClass().getName(), "test: IMEI 1 =" + DeviceUtils.getIMEI(getContext(), 1));
        Log.e(getClass().getName(), "test: MEID =" + DeviceUtils.getMEID(getContext()));
    }

    @Override
    public void onItemClick(int position) {

    }
}
