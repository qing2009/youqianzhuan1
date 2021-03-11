package com.shanqb.weizhuanzushou.tabview;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.shanqb.weizhuanzushou.R;
import com.shanqb.weizhuanzushou.activity.WithdrawActivity;
import com.shanqb.weizhuanzushou.adapter.ChannelAdapter;
import com.shanqb.weizhuanzushou.adapter.HomeTopListAdapter;
import com.shanqb.weizhuanzushou.bean.ChannelBean;
import com.shanqb.weizhuanzushou.test.BaseRecyclerViewAdapter;
import com.shanqb.weizhuanzushou.utils.DeviceUtils;
import com.shanqb.weizhuanzushou.utils.Global;
import com.shanqb.weizhuanzushou.utils.SharedPreConstants;
import com.shanqb.weizhuanzushou.utils.SharedPreferencesUtil;
import com.shanqb.weizhuanzushou.utils.sdk.AibianxianUtils;
import com.shanqb.weizhuanzushou.utils.sdk.JuxiangyouUtils;
import com.shanqb.weizhuanzushou.utils.sdk.Taojing91Utils;
import com.shanqb.weizhuanzushou.utils.sdk.XianWangUtils;
import com.shanqb.weizhuanzushou.utils.sdk.XiquUtils;
import com.shanqb.weizhuanzushou.view.CircleImageView;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;
import com.xuexiang.xui.widget.textview.marqueen.MarqueeFactory;
import com.xuexiang.xui.widget.textview.marqueen.MarqueeView;
import com.xuexiang.xui.widget.textview.marqueen.SimpleNoticeMF;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by yx on 16/4/3.
 */
public class HomePageFragment extends BaseFragment implements ITabClickListener, BannerLayout.OnBannerItemClickListener {
    //    @BindView(R.id.bl_horizontal)
//    BannerLayout blHorizontal;
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
    @BindView(R.id.userInfo_yaoqingma_textView)
    TextView userYouqingmaSuperText;
    @BindView(R.id.marqueeView1)
    MarqueeView marqueeView1;

//    private RecyclerViewBannerAdapter2 mAdapterHorizontal;//轮播图

    //赚金top10
    private LinearLayoutManager layoutManager;
    private HomeTopListAdapter adapter;

    //sdk入口
    private GridLayoutManager channelLayoutManager;
    private ChannelAdapter channelAdapter;

    @Override
    public void fetchData() {
        try {
            userNameTextView.setText(SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.loginCode, ""));
            String totalRevenue = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.allAmt, "0.00");
            totalRevenueTextView.setText(totalRevenue);
            String withdrawable = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.txAmt, "0.00");
            withdrawableTextView.setText(withdrawable);
            String yaoqingma = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.shareCode, "");
            userYouqingmaSuperText.setText(getString(R.string.yaoqingma) + ": " + yaoqingma);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        merCode = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.merCode, "");


        //公告
        final List<String> datas = Arrays.asList("公告：试玩前一定要看清楚试玩规则","公告：请使用正常手机试玩");
        MarqueeFactory<TextView, String> marqueeFactory1 = new SimpleNoticeMF(getContext());
        marqueeView1.setMarqueeFactory(marqueeFactory1);
        marqueeView1.startFlipping();
        marqueeFactory1.setData(datas);



        int screenWidth = DensityUtils.getScreenMetrics(true).widthPixels;//屏幕宽度

        int spacingWidth = DensityUtils.dp2px(15);//图片控件左右中间使用的空白间距宽度
//        int viewWidth = (screenWidth - spacingWidth) / 3;//总共两张图，每张图的宽度。
//        int viewHeight = viewWidth * 675 / 1080;//640*280是图片分辨率

        //计算homeView图片的高度
//        int homeViewHeight = screenWidth * 734 / 1180;
//        homeViewImagview.getLayoutParams().height = homeViewHeight;


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.home_view);
//        int viewWidth = taojing91Btn.getWidth();
        int viewWidth = screenWidth - spacingWidth;
        int viewHeight = viewWidth * bitmap.getHeight() / bitmap.getWidth();
        taojing91Btn.getLayoutParams().height = viewHeight;

        Log.e(getTag(), "viewWidth: " + viewWidth);
        Log.e(getTag(), "viewHeight: " + viewHeight);
//        aibianxianBtn.getLayoutParams().height = viewHeight;
//        juxiangwangBtn.getLayoutParams().height = viewHeight;
//        aibianxianBtn.getLayoutParams().height = viewHeight;
//        lin2Img2ImgView.getLayoutParams().height = viewHeight;
//        lin3Img1ImgView.getLayoutParams().height = viewHeight;
//        lin3Img2ImgView.getLayoutParams().height = viewHeight;


//        blHorizontal.setAdapter(mAdapterHorizontal = new RecyclerViewBannerAdapter2(DemoDataProvider.urls));
//        mAdapterHorizontal.setOnBannerItemClickListener(this);
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
                                                    YwSDK.Companion.refreshAppSecret(channelBean.getChannelKey(), channelBean.getChannelUser());
                                                    //进入鱼玩盒子首页
                                                    YwSDK_WebActivity.Companion.open(getActivity());
                                                    break;
                                                case Global.CHANNEL_CODE_DUOYOU:
                                                    DyAdApi.getDyAdApi().init(getActivity(), channelBean.getChannelUser(), channelBean.getChannelKey(), "channel");
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
//        String merCode = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.merCode, "");
//
//        DyAdApi.getDyAdApi().init(getActivity(), "dy_59634987", "be9ef50f987cc6ed577b726e6bde749c","channel");
////        DyAdApi.getDyAdApi().init(getActivity(),"dy_59633678", "ee0a8ee5de2ce442c8b094410440ec8c", "channel");
//        /**
//         * userId : 开发者APP用户标识，代表一个用户的Id，保证唯一性
//         * advertType: 0（默认值）显示全部数据  1.手游  2.棋牌游戏
//         */
//        DyAdApi.getDyAdApi().jumpAdList(getActivity(), merCode, 0);

    }

    @OnClick(R.id.ketixian_linLayout)
    public void onClick() {
        startActivity(new Intent(getActivity(), WithdrawActivity.class));
    }
}
