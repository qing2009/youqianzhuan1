package com.shanqb.myTools.tabview;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.shanqb.myTools.R;
import com.shanqb.myTools.adapter.RecyclerViewBannerAdapter2;
import com.shanqb.myTools.taojin91.DemoUtil;
import com.shanqb.myTools.utils.DemoDataProvider;
import com.shanqb.myTools.utils.DownloadCompleteReceiver;
import com.shanqb.myTools.utils.DownloadUtils;
import com.shanqb.myTools.utils.Utils;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    @OnClick({R.id.home_mahua, R.id.home_xiaoshuo, R.id.home_xinwen, R.id.home_kuaizhuan, R.id.image5,R.id.image6,R.id.image7,R.id.image8})
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
            case R.id.image5:
//                Utils.goWeb(getContext(), "http://8.133.178.205/admin/front/index/downapp?appName=vx8.0.6");
                downloadAPK(this.getView());
                break;
            case R.id.image6:
                Toast.makeText(getActivity(),"赶工中...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.image7:
                Toast.makeText(getActivity(),"赶工中...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.image8:
                Toast.makeText(getActivity(),"赶工中...",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemClick(int position) {

    }

    public void downloadAPK(View v) {
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
                    String path = "http://8.133.178.205/app/vx8.0.6.apk";
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
                            SystemClock.sleep(50);
                        }

                        fos.close();
                        is.close();
                    }
                    //9. 下载完成, 关闭, 进入3)
                    connection.disconnect();
                    dialog.dismiss();
                    installAPK();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 启动安装APK
     */
    private void installAPK() {
        Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        startActivity(intent);
    }
}
