package com.shanqb.youyou.tabview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import com.shanqb.youyou.BaseApplication;
import com.shanqb.youyou.FloatView.FloatView;
import com.shanqb.youyou.R;
import com.shanqb.youyou.activity.WithdrawActivity;
import com.shanqb.youyou.adapter.ChannelAdapter;
import com.shanqb.youyou.adapter.HomeTopListAdapter;
import com.shanqb.youyou.aibianxian.H5ActivityOptABX;
import com.shanqb.youyou.bean.BaseJsonResponse2;
import com.shanqb.youyou.bean.ChannelBean;
import com.shanqb.youyou.bean.LoginResponse;
import com.shanqb.youyou.bean.ZhuanjinTopResponse;
import com.shanqb.youyou.duoyou.H5ActivityOptDY;
import com.shanqb.youyou.inter.MyQueueResponse;
import com.shanqb.youyou.taojin91.H5ActivityOpt;
import com.shanqb.youyou.test.BaseRecyclerViewAdapter;
import com.shanqb.youyou.utils.ActionConstants;
import com.shanqb.youyou.utils.DeviceUtils;
import com.shanqb.youyou.utils.Global;
import com.shanqb.youyou.utils.SharedPreConstants;
import com.shanqb.youyou.utils.SharedPreferencesUtil;
import com.shanqb.youyou.utils.sdk.AibianxianUtils;
import com.shanqb.youyou.utils.sdk.JuxiangyouUtils;
import com.shanqb.youyou.utils.sdk.Taojing91Utils;
import com.shanqb.youyou.utils.sdk.XianWangUtils;
import com.shanqb.youyou.utils.sdk.XiquUtils;
import com.shanqb.youyou.view.CircleImageView;
import com.shanqb.youyou.xgame.H5ActivityOptX;
import com.xianwan.sdklibrary.helper.XWADPage;
import com.xianwan.sdklibrary.helper.XWADPageConfig;
import com.xianwan.sdklibrary.helper.XWAdSdk;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;
import com.xuexiang.xui.widget.textview.marqueen.MarqueeFactory;
import com.xuexiang.xui.widget.textview.marqueen.MarqueeView;
import com.xuexiang.xui.widget.textview.marqueen.SimpleNoticeMF;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jfq.wowan.com.myapplication.PlayMeUtil;


/**
 * Created by yx on 16/4/3.
 */
public class HomePageFragment extends BaseFragment implements ITabClickListener, BannerLayout.OnBannerItemClickListener, MyQueueResponse {

    //个人信息
    @BindView(R.id.headImageView)
    CircleImageView headImageView;
    @BindView(R.id.userNameTextView)
    TextView userNameTextView;
    @BindView(R.id.phoneTextView)
    TextView phoneTextView;
    @BindView(R.id.userInfo_yaoqingma_textView)
    TextView userYouqingmaSuperText;
    @BindView(R.id.totalRevenueTextView)
    TextView totalRevenueTextView;
    @BindView(R.id.withdrawableTextView)
    TextView withdrawableTextView;

    //公告
    @BindView(R.id.marqueeView1)
    MarqueeView marqueeView1;

    //渠道
    @BindView(R.id.channel_recView)
    RecyclerView channelRecView;

    //赚金top10
    @BindView(R.id.homeView_imagview)
    ImageView homeViewImagview;//赚金top10 title图片
    @BindView(R.id.recordRecyView)
    RecyclerView recordRecyView;//赚金top10列表

//    private RecyclerViewBannerAdapter2 mAdapterHorizontal;//轮播图

    //公告
    private List<String> gonggaoList;
    private MarqueeFactory<TextView, String> marqueeFactory1;

    //渠道
    private GridLayoutManager channelLayoutManager;
    private ChannelAdapter channelAdapter;

    //赚金top10
    private LinearLayoutManager layoutManager;
    private HomeTopListAdapter adapter;

    private Unbinder unbinder;
    private String merCode;
    private String oaid;

    private WindowManager windowManager = null;
    private WindowManager.LayoutParams windowManagerParams = null;
    private FloatView floatView = null;

    @Override
    public void fetchData() {
        try {
//            getTop10();

            userNameTextView.setText(SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.loginCode, ""));

            String yaoqingma = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.shareCode, "");
            userYouqingmaSuperText.setText(getString(R.string.yaoqingma) + ": " + yaoqingma);

            setShouyiView();

            setGonggao();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 显示收益
     */
    private void setShouyiView() {
        Log.e("HomePageFragment", "setShouyiView() called");
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

    /**
     * 设置公告
     */
    private void setGonggao() {
        String gonggaoString = SharedPreferencesUtil.getStringValue(getContext(), SharedPreConstants.gonggao,"");
        if (!TextUtils.isEmpty(gonggaoString)){
            String[] strings = gonggaoString.split(getString(R.string.gonggaoSplit));
            gonggaoList = Arrays.asList(strings);

            marqueeFactory1 = new SimpleNoticeMF(getContext());
            marqueeView1.setMarqueeFactory(marqueeFactory1);
            marqueeView1.startFlipping();
            marqueeFactory1.setData(gonggaoList);
            marqueeView1.setVisibility(View.VISIBLE);
        }else {
            marqueeView1.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
//        createFloatView();
        initData();


        int screenWidth = DensityUtils.getScreenMetrics(true).widthPixels;//屏幕宽度
        int leftSpacingWidth = getResources().getDimensionPixelSize(R.dimen.screen_margin_horizontal);//图片控件和左侧屏幕边框的距离

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.homepage_taojin91);
        int viewWidth = screenWidth - leftSpacingWidth*2;
        int viewHeight = viewWidth * bitmap.getHeight() / bitmap.getWidth();
        homeViewImagview.getLayoutParams().height = viewHeight;

        Log.e(getTag(), "viewWidth: " + viewWidth);
        Log.e(getTag(), "viewHeight: " + viewHeight);


        //channel数据
        String channelListJson = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.channelList, "");
        if (channelListJson != null) {

            Type type = new TypeToken<List<ChannelBean>>() {}.getType();
            List<ChannelBean> channelBeanList = new Gson().fromJson(channelListJson, type);
            channelAdapter = new ChannelAdapter(getContext(),channelBeanList);
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
                                                    if (channelBean.getWay().equals("0")){//sdk打开
                                                        AibianxianUtils.startSDK(getActivity().getApplication(), merCode, getActivity(), channelBean.getChannelUser(), channelBean.getChannelKey());
                                                    }else {//h5打开
                                                        //使用内置webview打开H5连接
                                                        Intent intent=new Intent(getActivity(), H5ActivityOptABX.class);
                                                        intent.putExtra("appid",channelBean.getChannelUser());
                                                        intent.putExtra("appkey",channelBean.getChannelKey());
                                                        startActivity(intent);
                                                    }
                                                    break;
                                                case Global.CHANNEL_CODE_JUXIANGYOU:
                                                    JuxiangyouUtils.startSDK(getActivity(), merCode, channelBean.getChannelUser(), channelBean.getChannelKey(),oaid);
                                                    break;
                                                case Global.CHANNEL_CODE_TAOJING91:
                                                    if (channelBean.getWay().equals("0")){//sdk打开
                                                        Taojing91Utils.startSDK(getActivity(), merCode, channelBean.getChannelUser(), channelBean.getChannelKey());
                                                    }else {//h5打开
                                                        //使用内置webview打开H5连接
                                                        Intent intent=new Intent(getActivity(), H5ActivityOpt.class);
                                                        intent.putExtra("appid",channelBean.getChannelUser());
                                                        intent.putExtra("appkey",channelBean.getChannelKey());
                                                        startActivity(intent);
                                                    }
                                                    break;
                                                case Global.CHANNEL_CODE_XGAME:
                                                    if (channelBean.getWay().equals("0")){//sdk打开
                                                        Taojing91Utils.startSDK(getActivity(), merCode, channelBean.getChannelUser(), channelBean.getChannelKey());
                                                    }else {//h5打开
                                                        //使用内置webview打开H5连接
                                                        Intent intent=new Intent(getActivity(), H5ActivityOptX.class);
                                                        intent.putExtra("appid",channelBean.getChannelUser());
                                                        intent.putExtra("appkey",channelBean.getChannelKey());
                                                        startActivity(intent);
                                                    }
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
                                                    if (channelBean.getWay().equals("0")){//sdk打开
                                                        DyAdApi.getDyAdApi().init(getActivity(), channelBean.getChannelUser(), channelBean.getChannelKey(), "channel");
                                                        /**
                                                         * userId : 开发者APP用户标识，代表一个用户的Id，保证唯一性
                                                         * advertType: 0（默认值）显示全部数据  1.手游  2.棋牌游戏
                                                         */
                                                        DyAdApi.getDyAdApi().jumpAdList(getActivity(), merCode, 0);
                                                    }else {//h5打开
                                                        //使用内置webview打开H5连接
                                                        Intent intent=new Intent(getActivity(), H5ActivityOptDY.class);
                                                        intent.putExtra("appid",channelBean.getChannelUser());
                                                        intent.putExtra("appkey",channelBean.getChannelKey());
                                                        startActivity(intent);
                                                    }
                                                    break;
                                                case Global.CHANNEL_CODE_XIQU:
                                                    XiquUtils.init(getActivity().getApplication(), channelBean.getChannelUser(), channelBean.getChannelKey());
                                                    XiquUtils.startSDK(getActivity(), merCode);
                                                    break;
                                                case Global.CHANNEL_CODE_WOWANG:
                                                    String deviceId = DeviceUtils.getDeviceId(getActivity());
                                                    PlayMeUtil.openIndex(getActivity(),channelBean.getChannelUser(),merCode,deviceId,oaid,channelBean.getChannelKey());
//                                                    PlayMeUtil.openIndex(getActivity(),"6968",merCode,deviceId,oaid,"z7ugkaIgSvvvaTg0jFmeBdHDE2p15uHh");
//                                                  PlayMeUtil.openIndex(getActivity(), "3888", "1443910", deviceId, "dewfew-fregf-gfreg-gre", "Ax5xVDDx9NGbIhefGzqf9S8pT7aM8E72");
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
                                        } else {
                                            Toast.makeText(getActivity(),channelBean.getTishi(),Toast.LENGTH_SHORT).show();
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

    private void initData() {
        merCode = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.merCode, "");
        oaid = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.OAID,"");
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


    private void test() {
        Log.e(getClass().getName(), "test: DeviceId=" + DeviceUtils.getDeviceId(getContext()));
        Log.e(getClass().getName(), "test: IMEI 0 =" + DeviceUtils.getIMEI(getContext(), 0));
        Log.e(getClass().getName(), "test: IMEI 1 =" + DeviceUtils.getIMEI(getContext(), 1));
        Log.e(getClass().getName(), "test: MEID =" + DeviceUtils.getMEID(getContext()));
    }

    @Override
    public void onItemClick(int position) {

    }

    @OnClick({R.id.ketixian_linLayout,R.id.upAmt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ketixian_linLayout:
                startActivity(new Intent(getActivity(), WithdrawActivity.class));
                break;
            case R.id.upAmt:
                upShouyiView();
                break;
        }

    }


    public void getTop10() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(ActionConstants.LOGIN_businessCode, Global.BUSINESS_CODE);
        map.put(ActionConstants.ORDERLIST_PAGE, "1");
        map.put(ActionConstants.ORDERLIST_SIZE, "10");
        requestPostQueue(false,ActionConstants.getMerTop,map,this);
    }

    @Override
    public void onResponse(String requestAction,String response) {
        ZhuanjinTopResponse responseBean = new Gson().fromJson(response, new TypeToken<ZhuanjinTopResponse>() {
        }.getType());
        if (responseBean != null && BaseJsonResponse2.STATE_SUCCESS.equals(responseBean.getState()) && responseBean.getData()!=null && responseBean.getData().size()>0) {

            //赚金top10
            this.layoutManager = new LinearLayoutManager(getActivity());
            this.layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            this.recordRecyView.setLayoutManager(this.layoutManager);
            adapter = new HomeTopListAdapter(getActivity(), responseBean.getData());
            recordRecyView.setAdapter(adapter);
            recordRecyView.setVisibility(View.VISIBLE);
//            top10NodataTextView.setVisibility(View.GONE);
        }else {
            recordRecyView.setVisibility(View.GONE);
//            top10NodataTextView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onErrorResponse(String requestAction,VolleyError error) {

    }

    /**
     * 显示收益
     */
    private void upShouyiView() {
        Log.e("ProfileFragment", "setShouyiView() called");
        showLoadingDialog();

        String loginUrl = Global.BASE_INTER_URL + ActionConstants.INTER_MYINFO;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(getClass().toString(), response);
                dismissLoadingDialog();

                LoginResponse loginResponse = new Gson().fromJson(response, new TypeToken<LoginResponse>() {
                }.getType());
                if (loginResponse != null) {
                    if (loginResponse.isSuccess()) {
                        if (loginResponse.getData() != null) {
                            SharedPreferencesUtil.setStringValue(getActivity(), SharedPreConstants.allAmt, loginResponse.getData().getAllAmt()+ "");
                            SharedPreferencesUtil.setStringValue(getActivity(), SharedPreConstants.txAmt, loginResponse.getData().getTxAmt()+ "");
                            totalRevenueTextView.setText(loginResponse.getData().getAllAmt()+ "");
                            withdrawableTextView.setText(loginResponse.getData().getTxAmt()+ "");
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissLoadingDialog();
                Log.e(getClass().toString(), error.getMessage(), error);
//                    Toast.makeText(LoginActivity.this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();  //登录失败提示
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String merCode = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.merCode, "");
                Map<String, String> map = new HashMap<String, String>();
                map.put(ActionConstants.LOGIN_businessCode, Global.BUSINESS_CODE);
                map.put(ActionConstants.MERCODE, merCode);
                return map;
            }
        };
        mQueue.add(stringRequest);
    }

    public void createFloatView(){
        Toast.makeText(getActivity(),"进入",Toast.LENGTH_SHORT).show();
        floatView = new FloatView(getActivity().getApplicationContext());
        floatView.setImageResource(R.drawable.ic_my_icon);
        windowManager = (WindowManager) getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManagerParams = ((BaseApplication) getActivity().getApplication()).getWindowParams();
        windowManagerParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        windowManagerParams.format = PixelFormat.RGBA_8888;
        windowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        windowManagerParams.gravity = Gravity.RIGHT | Gravity.CENTER;
//        windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
        windowManagerParams.x = 0;
        windowManagerParams.y = 0;
        windowManagerParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManagerParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManager.addView(floatView, windowManagerParams);
    }
}
