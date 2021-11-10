package com.shanqb.myTools.tabview;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.component.dly.xzzq_ywsdk.YwSDK;
import com.component.dly.xzzq_ywsdk.YwSDK_WebActivity;
import com.duoyou.task.openapi.DyAdApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallback;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.shanqb.myTools.R;
import com.shanqb.myTools.adapter.AppsListAdapter;
import com.shanqb.myTools.adapter.ChannelAdapter;
import com.shanqb.myTools.adapter.RecyclerViewBannerAdapter2;
import com.shanqb.myTools.aibianxian.H5ActivityOptABX;
import com.shanqb.myTools.bean.AppsBean;
import com.shanqb.myTools.bean.ChannelBean;
import com.shanqb.myTools.duoyou.H5ActivityOptDY;
import com.shanqb.myTools.taojin91.AppUtil;
import com.shanqb.myTools.taojin91.H5ActivityOpt;
import com.shanqb.myTools.test.BaseRecyclerViewAdapter;
import com.shanqb.myTools.utils.DemoDataProvider;
import com.shanqb.myTools.utils.DeviceUtils;
import com.shanqb.myTools.utils.Global;
import com.shanqb.myTools.utils.SharedPreConstants;
import com.shanqb.myTools.utils.SharedPreferencesUtil;
import com.shanqb.myTools.utils.Utils;
import com.shanqb.myTools.utils.sdk.AibianxianUtils;
import com.shanqb.myTools.utils.sdk.JuxiangyouUtils;
import com.shanqb.myTools.utils.sdk.Taojing91Utils;
import com.shanqb.myTools.utils.sdk.XianWangUtils;
import com.shanqb.myTools.utils.sdk.XiquUtils;
import com.shanqb.myTools.xgame.H5ActivityOptX;
import com.xianwan.sdklibrary.helper.XWADPage;
import com.xianwan.sdklibrary.helper.XWADPageConfig;
import com.xianwan.sdklibrary.helper.XWAdSdk;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jfq.wowan.com.myapplication.PlayMeUtil;

/**
 * Created by yx on 16/4/3.
 */
public class HomeFragment extends BaseFragment implements ITabClickListener, BannerLayout.OnBannerItemClickListener {

    Unbinder unbinder;

//    @BindView(R.id.taskGetMoney_imgView)
//    RoundCornerImageView taskGetMoneyImgView;
    @BindView(R.id.bl_horizontal)
    BannerLayout blHorizontal;//轮播图

    private RecyclerViewBannerAdapter2 mAdapterHorizontal;//轮播图

    private ViewPager mViewPager;

    @BindView(R.id.home_kuaizhuan)
    ImageView imageView;

    private AppsListAdapter appAdapter;
    //渠道
    @BindView(R.id.apps_recView)
    RecyclerView appsRecView;
    private GridLayoutManager appsLayoutManager;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    //浮动按钮按下时x坐标
    private float downX;
    //浮动按钮按下时y坐标
    private float downY;
    private File apkFile;
    /**
     * 按下时浮层x坐标
     */
    float downViewX = 0;

    @Override
    public void fetchData() {

        setShouyiView();
    }

    /**
     * 显示收益
     */
    private void setShouyiView() {
        Log.e("ProfileFragment", "setShouyiView() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewPager = getActivity().findViewById(R.id.viewpager);
        //刷新总收益、可提现
        setShouyiView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        //轮播图
        blHorizontal.setAdapter(mAdapterHorizontal = new RecyclerViewBannerAdapter2(DemoDataProvider.urls));
        mAdapterHorizontal.setOnBannerItemClickListener(this);
        //app数据
        String appListJson = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.appList, "");
        if (appListJson != null) {
            Type type = new TypeToken<List<AppsBean>>() {}.getType();
            List<AppsBean> appsBeanList = new Gson().fromJson(appListJson, type);
            List<AppsBean> appsList = new ArrayList<>();
            String appIds = SharedPreferencesUtil.getStringValue(getActivity(), SharedPreConstants.appIds, "");
            if (appIds != null && !appIds.equals("")) {
                String[] ids = appIds.split(",");
                for (String id : ids) {
                    for (AppsBean ab : appsBeanList) {
                        if (id.equals(ab.getId()+"")) {
                            appsList.add(ab);
                        }
                    }
                }
            }
            appAdapter = new AppsListAdapter(getContext(),appsList);
            appAdapter.setItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
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

                                        AppsBean appsBean = appsBeanList.get(var2);
                                        if (appsBean.getAppUrl() != null && !appsBean.getAppUrl().equals("")) {
                                            downloadAPK(getView(),appsBean.getAppUrl());
                                        } else {
                                            Toast.makeText(getActivity(),"系统繁忙，请稍后重试！",Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
//                                    Toast.makeText(getActivity(), "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            });

            appsLayoutManager = new GridLayoutManager(getActivity(), 1);
            appsRecView.setLayoutManager(appsLayoutManager);
            appsRecView.addItemDecoration(new GridDividerItemDecoration(getContext(), 1, DensityUtils.dp2px(5)));

            appsRecView.setHasFixedSize(true);

            appsRecView.setAdapter(appAdapter);
        }
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
    }

    @OnClick({R.id.home_mahua, R.id.home_xiaoshuo, R.id.home_xinwen, R.id.home_kuaizhuan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_mahua:
                Utils.goWeb(getContext(), "https://wap.52pk.com/news/");
                break;
            case R.id.home_xiaoshuo:
                Utils.goWeb(getContext(), "http://m.yzz.cn/news/");
                break;
            case R.id.home_xinwen:
                Utils.goWeb(getContext(), "http://news.uuu9.com/m/");
                break;
            case R.id.home_kuaizhuan:
                mViewPager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onItemClick(int position) {

    }

    public void downloadAPK(View v,String url) {
        //1). 主线程, 显示提示视图: ProgressDialog
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressNumberFormat("%1d kb / %2d kb");
        dialog.setTitle("下载中...");
        dialog.show();

        //准备用于保存APK文件的File对象 : /storage/sdcard/Android/package_name/files/xxx.apk
        apkFile = new File(getContext().getExternalFilesDir(null), "wechat.apk");

        //2). 启动分线程, 请求下载APK文件, 下载过程中显示下载进度
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //1. 得到连接对象
                    String path = url;
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //2. 设置
                    //connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(10000);
                    //3. 连接
                    connection.connect();
                    //4. 请求并得到响应码200
                    int responseCode = connection.getResponseCode();
                    if(responseCode==200) {
                        //设置dialog的最大进度
                        dialog.setMax(connection.getContentLength()/1024);


                        //5. 得到包含APK文件数据的InputStream
                        InputStream is = connection.getInputStream();
                        //6. 创建指向apkFile的FileOutputStream
                        FileOutputStream fos = new FileOutputStream(apkFile);
                        //7. 边读边写
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while((len=is.read(buffer))!=-1) {
                            fos.write(buffer, 0, len);
                            //8. 显示下载进度
                            dialog.incrementProgressBy(len/1024);

                            //休息一会(模拟网速慢)
                            //Thread.sleep(50);
//                            SystemClock.sleep(50);
                        }

                        fos.close();
                        is.close();
                    }
                    //9. 下载完成, 关闭, 进入3)
                    connection.disconnect();
                    dialog.dismiss();
                    AppUtil.installApp(getContext(),apkFile);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
