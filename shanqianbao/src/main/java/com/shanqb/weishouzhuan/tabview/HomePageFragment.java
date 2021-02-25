package com.shanqb.weishouzhuan.tabview;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallback;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.shanqb.weishouzhuan.R;
import com.shanqb.weishouzhuan.activity.ReadGetMoneyActivity;
import com.shanqb.weishouzhuan.adapter.ChannelAdapter;
import com.shanqb.weishouzhuan.adapter.HomeTopListAdapter;
import com.shanqb.weishouzhuan.adapter.RecyclerViewBannerAdapter2;
import com.shanqb.weishouzhuan.bean.ChannelBean;
import com.shanqb.weishouzhuan.bean.LoginResponse;
import com.shanqb.weishouzhuan.test.BaseRecyclerViewAdapter;
import com.shanqb.weishouzhuan.utils.DemoDataProvider;
import com.shanqb.weishouzhuan.utils.DeviceUtils;
import com.shanqb.weishouzhuan.utils.Global;
import com.shanqb.weishouzhuan.utils.SharedPreConstants;
import com.shanqb.weishouzhuan.utils.SharedPreferencesUtil;
import com.shanqb.weishouzhuan.utils.sdk.AibianxianUtils;
import com.shanqb.weishouzhuan.utils.sdk.JuxiangyouUtils;
import com.shanqb.weishouzhuan.utils.sdk.Taojing91Utils;
import com.shanqb.weishouzhuan.utils.sdk.XianWangUtils;
import com.shanqb.weishouzhuan.utils.sdk.XiquUtils;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


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
    @BindView(R.id.channel_recView)
    RecyclerView channelRecView;

    private RecyclerViewBannerAdapter2 mAdapterHorizontal;

    //赚金top10
    private LinearLayoutManager layoutManager;
    private HomeTopListAdapter adapter;

    //sdk入口
    private GridLayoutManager channelLayoutManager;
    private ChannelAdapter channelAdapter;

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


        blHorizontal.setAdapter(mAdapterHorizontal = new RecyclerViewBannerAdapter2(DemoDataProvider.urls));
        mAdapterHorizontal.setOnBannerItemClickListener(this);

        this.layoutManager = new LinearLayoutManager(getActivity());
        this.layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.recordRecyView.setLayoutManager(this.layoutManager);
        adapter = new HomeTopListAdapter(getActivity());
        recordRecyView.setAdapter(adapter);


        //channel数据
        String channelListJson = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.channelList, "");
        if (channelListJson!=null){

            Type type = new TypeToken<List<ChannelBean>>() {}.getType();
            List<ChannelBean> channelBeanList = new Gson().fromJson(channelListJson, type);
            channelAdapter = new ChannelAdapter(channelBeanList);
            channelAdapter.setItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View var1, int var2) {

                    PermissionX.init(getActivity())
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
                            .request(new RequestCallback() {
                                @Override
                                public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                                    if (allGranted) {

                                        String merCode = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.merCode, "");
                                        ChannelBean channelBean = channelBeanList.get(var2);

                                        switch (channelBean.getChannelCode()){
                                            case Global.CHANNEL_CODE_AIBIANXIAN:
                                                AibianxianUtils.startSDK(getActivity().getApplication(), merCode, getActivity(),channelBean.getChannelUser(),channelBean.getChannelKey());
                                                break;
                                            case Global.CHANNEL_CODE_JUXIANGYOU:
                                                JuxiangyouUtils.startSDK(getActivity(), merCode,channelBean.getChannelUser(),channelBean.getChannelKey());
                                                break;
                                            case Global.CHANNEL_CODE_TAOJING91:
                                                Taojing91Utils.startSDK(getActivity(), merCode,channelBean.getChannelUser(),channelBean.getChannelKey());
                                                break;
                                            case Global.CHANNEL_CODE_XIANWANG:
                                                XianWangUtils.startSDK(getActivity(),channelBean.getChannelUser(),channelBean.getChannelKey());
                                                break;
                                            case Global.CHANNEL_CODE_XIQU:
                                                XiquUtils.init(getActivity().getApplication(),channelBean.getChannelUser(),channelBean.getChannelKey());
                                                XiquUtils.startSDK(getActivity(), merCode);
                                                break;
                                        }

                                    } else {
//                                    Toast.makeText(getActivity(), "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            });

            channelLayoutManager = new GridLayoutManager(getActivity(),2);
            channelRecView.setLayoutManager(channelLayoutManager);
            channelRecView.addItemDecoration(new GridDividerItemDecoration(getContext(), 2, DensityUtils.dp2px(5)));

            channelRecView.setHasFixedSize(true);

            channelRecView.setAdapter(channelAdapter);
        }else {
            channelRecView.setVisibility(View.GONE);
        }



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
