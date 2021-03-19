package com.shanqb.zhimazhuan.tabview;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.component.dly.xzzq_ywsdk.YwSDK;
import com.component.dly.xzzq_ywsdk.YwSDK_WebActivity;
import com.duoyou.task.openapi.DyAdApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallback;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.shanqb.shanqianbao.MainActivity;
import com.shanqb.zhimazhuan.R;
import com.shanqb.zhimazhuan.adapter.ChannelAdapter;
import com.shanqb.zhimazhuan.adapter.HomeTopListAdapter;
import com.shanqb.zhimazhuan.adapter.RecyclerViewBannerAdapter2;
import com.shanqb.zhimazhuan.bean.ChannelBean;
import com.shanqb.zhimazhuan.test.BaseRecyclerViewAdapter;
import com.shanqb.zhimazhuan.utils.DemoDataProvider;
import com.shanqb.zhimazhuan.utils.DeviceUtils;
import com.shanqb.zhimazhuan.utils.Global;
import com.shanqb.zhimazhuan.utils.SharedPreConstants;
import com.shanqb.zhimazhuan.utils.SharedPreferencesUtil;
import com.shanqb.zhimazhuan.utils.sdk.AibianxianUtils;
import com.shanqb.zhimazhuan.utils.sdk.JuxiangyouUtils;
import com.shanqb.zhimazhuan.utils.sdk.Taojing91Utils;
import com.shanqb.zhimazhuan.utils.sdk.XianWangUtils;
import com.shanqb.zhimazhuan.utils.sdk.XiquUtils;
import com.xianwan.sdklibrary.helper.XWADPage;
import com.xianwan.sdklibrary.helper.XWADPageConfig;
import com.xianwan.sdklibrary.helper.XWAdSdk;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.channel_recView)
    RecyclerView channelRecView;
    @BindView(R.id.homeView_imagview)
    ImageView homeViewImagview;

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

        //计算homeView图片的高度
        int homeViewHeight = screenWidth * 734 / 1180;
        homeViewImagview.getLayoutParams().height = homeViewHeight;

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
//
//        this.layoutManager = new LinearLayoutManager(getActivity());
//        this.layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        this.recordRecyView.setLayoutManager(this.layoutManager);
//        adapter = new HomeTopListAdapter(getActivity());
//        recordRecyView.setAdapter(adapter);
        recordRecyView.setVisibility(View.GONE);


        //channel数据
        String channelListJson = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.channelList, "");
        if (channelListJson != null) {

            Type type = new TypeToken<List<ChannelBean>>() {
            }.getType();
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

                                        String oaid = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.OAID,"");

                                        String merCode = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.merCode, "");
                                        ChannelBean channelBean = channelBeanList.get(var2);
                                        if ("1".equals(channelBean.getState())) {


                                            switch (channelBean.getChannelCode()) {
                                                case Global.CHANNEL_CODE_AIBIANXIAN:
                                                    AibianxianUtils.startSDK(getActivity().getApplication(), merCode, getActivity(), channelBean.getChannelUser(), channelBean.getChannelKey());
                                                    break;
                                                case Global.CHANNEL_CODE_JUXIANGYOU:
                                                    JuxiangyouUtils.startSDK(getActivity(), merCode, channelBean.getChannelUser(), channelBean.getChannelKey());
                                                    break;
                                                case Global.CHANNEL_CODE_TAOJING91:
                                                    Taojing91Utils.startSDK(getActivity(), merCode, channelBean.getChannelUser(), channelBean.getChannelKey());
                                                    break;
                                                case Global.CHANNEL_CODE_XIANWANG:
                                                    XianWangUtils.startSDK(getActivity(), channelBean.getChannelUser(), channelBean.getChannelKey());
                                                    break;
                                                case Global.CHANNEL_CODE_YUWANG:
                                                    YwSDK.Companion.refreshAppSecret(channelBean.getChannelKey(),channelBean.getChannelUser());
                                                    //进入鱼玩盒子首页
                                                    YwSDK_WebActivity.Companion.open(getActivity());
                                                    break;
                                                case Global.CHANNEL_CODE_DUOYOU:
                                                    DyAdApi.getDyAdApi().init(getActivity(), channelBean.getChannelUser(), channelBean.getChannelKey(),"channel");
                                                    /**
                                                     * userId : 开发者APP用户标识，代表一个用户的Id，保证唯一性
                                                     * advertType: 0（默认值）显示全部数据  1.手游  2.棋牌游戏
                                                     */
                                                    DyAdApi.getDyAdApi().jumpAdList(getActivity(), merCode, 0);
                                                    break;
                                                case Global.CHANNEL_CODE_XIQU:
                                                    XiquUtils.init(getActivity().getApplication(), channelBean.getChannelUser(), channelBean.getChannelKey());
                                                    XiquUtils.startSDK(getActivity(), merCode);
                                                    break;
                                                case Global.CHANNEL_CODE_WOWANG:
                                                    String deviceId = DeviceUtils.getDeviceId(getActivity());
                                                    PlayMeUtil.openIndex(getActivity(),channelBean.getChannelUser(),merCode,deviceId,oaid,channelBean.getChannelKey());
//                                                    PlayMeUtil.openIndex(getActivity(),"6968",merCode,deviceId,oaid,"z7ugkaIgSvvvaTg0jFmeBdHDE2p15uHh");
//                            PlayMeUtil.openIndex(getActivity(), "3888", "1443910", deviceId, "dewfew-fregf-gfreg-gre", "Ax5xVDDx9NGbIhefGzqf9S8pT7aM8E72");

                                                    break;



                                                case Global.CHANNEL_CODE_XIANWANG2:
//                                                    XWAdSdk.init(getActivity().getApplication(), "1010", "nw2olixipulielgp"); //初始化 参数
                                                    XWAdSdk.init(getActivity().getApplication(), channelBean.getChannelUser(), channelBean.getChannelKey()); //初始化 参数
                                                    XWAdSdk.showLOG(true); //是否开启日志

                                                    XWADPage.jumpToAD(new XWADPageConfig.Builder(merCode) //必传参数，指接入方渠道的APP的用户ID，要求每个用户唯一，且不变
                                                            .pageType(XWADPageConfig.PAGE_AD_LIST)
                                                            .msaOAID(oaid)//指的是接入了安全联盟sdk后，获取的用户的oaid，获取不到可不用设置 或者传 空/null 不可乱传
                                                            .build());
                                                    break;
                                            }

                                        }
                                    } else {
//                                    Toast.makeText(getActivity(), "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            });

            channelLayoutManager = new GridLayoutManager(getActivity(), 2);
            channelRecView.setLayoutManager(channelLayoutManager);
            channelRecView.addItemDecoration(new GridDividerItemDecoration(getContext(), 2, DensityUtils.dp2px(5)));

            channelRecView.setHasFixedSize(true);

            channelRecView.setAdapter(channelAdapter);
        } else {
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
//        String oaid = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.OAID,"");
//
//        XWAdSdk.init(getActivity().getApplication(), "1010", "nw2olixipulielgp"); //初始化 参数
////        XWAdSdk.init(getActivity().getApplication(), channelBean.getChannelUser(), channelBean.getChannelKey()); //初始化 参数
//        XWAdSdk.showLOG(true); //是否开启日志
//
//        XWADPage.jumpToAD(new XWADPageConfig.Builder(merCode) //必传参数，指接入方渠道的APP的用户ID，要求每个用户唯一，且不变
//                .pageType(XWADPageConfig.PAGE_AD_LIST)
//                .msaOAID(oaid)//指的是接入了安全联盟sdk后，获取的用户的oaid，获取不到可不用设置 或者传 空/null 不可乱传
//                .build());

    }
}
