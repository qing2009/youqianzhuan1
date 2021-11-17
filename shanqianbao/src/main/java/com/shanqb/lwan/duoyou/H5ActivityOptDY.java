package com.shanqb.lwan.duoyou;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;
import com.shanqb.lwan.R;
import com.shanqb.lwan.taojin91.AppUtil;
import com.shanqb.lwan.taojin91.DemoUtil;
import com.shanqb.lwan.taojin91.PermissionUtil;
import com.shanqb.lwan.taojin91.TJOAIDUtil;
import com.shanqb.lwan.taojin91.WebChoosePicUtil;
import com.shanqb.lwan.utils.SharedPreConstants;
import com.shanqb.lwan.utils.SharedPreferencesUtil;
import com.shanqb.lwan.utils.sdk.DuoyouUtils;

import java.io.File;
import java.util.Locale;

/**
 * 淘金H5 Demo
 */
public class H5ActivityOptDY extends Activity {
    private static final String TAG = "TJH5";
    private WebView webView;
    private Handler handler;
    //图片上传功能，方便用上传截图反馈问题
    private WebChoosePicUtil picUtil;
    //下载失败重试测试次数
    private final int MaxRetryCount = 3;
    //重试时间间隔
    private final long RetryInterval = 5000;
    private int curRetryCount = MaxRetryCount;
    private DownloadListener1 downloadListener;
    private boolean stopRetry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        init();
        checkAndRequestPermission();
    }
    private void init(){
        handler = new Handler();
        webView = findViewById(R.id.webView);
        initWebSetting();
        setWebChromeClient();
        setWebViewClient();
        setWebDownloadListener();
        addWebJavascriptInterface();
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSetting(){
        WebSettings set = webView.getSettings();
        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        set.setJavaScriptEnabled(true);
        set.setDefaultTextEncodingName("UTF-8");
        set.setCacheMode(WebSettings.LOAD_DEFAULT);
        set.setBuiltInZoomControls(false);
        set.setUseWideViewPort(true);
        set.setLoadWithOverviewMode(true);
        set.setSupportZoom(false);
        set.setPluginState(WebSettings.PluginState.ON);
        set.setDomStorageEnabled(true);
        set.setLoadsImagesAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            set.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }
    private void setWebViewClient(){
        webView.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e(TAG,"shouldOverrideUrlLoading url-->"+request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG,"shouldOverrideUrlLoading url-->"+url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }
    private void setWebChromeClient(){
        webView.setWebChromeClient(new WebChromeClient(){
            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
                picUtil = new WebChoosePicUtil(H5ActivityOptDY.this,filePathCallback,null);
                picUtil.choicePic();
            }
            // For Android  >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                picUtil = new WebChoosePicUtil(H5ActivityOptDY.this,null,filePathCallback);
                picUtil.choicePic();
                return true;
            }
        });
    }

    /**
     * webview下载监听，此处是跳转外部浏览器下载，
     * 如果已经注入了下载优化的相关js方法，那么将不会收到回调
     */
    private void setWebDownloadListener(){
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 注入js，实现相关方法，
     * App下载器采用的是：https://github.com/lingochamp/okdownload
     * 开发者可以使用自己应用的下载器
     */
    private void addWebJavascriptInterface(){
        webView.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public void exit(){
                Log.e(TAG,"js call exit");
                activityExit();
            }
            @JavascriptInterface
            public int isInstall(String packageName) {
                int flag= AppUtil.appIsInstalled(getApplication(),packageName) ? 1:2;
                Log.e(TAG,"js call isInstall() pkg:"+packageName+",result:"+flag);
                return flag;
            }
            @JavascriptInterface
            public void openApp(String packageName) {
                Log.e(TAG,"js call openApp() pkg:"+packageName);
                AppUtil.openApp(H5ActivityOptDY.this,packageName);
            }
            @JavascriptInterface
            public void download(String url) {
                //执行下载相关逻辑，优化下载体验
                Log.e(TAG,"js call url:"+url);
                startTask(url);
            }
        },"partyMethod");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private void startTask(final String url){
        //File parentFile = DemoUtil.getParentFile(getApplicationContext());
        File parentFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/qianren/dyapk");
        if (!parentFile.getParentFile().exists()) {
            DemoUtil.crSDFile("qianren","dyapk");
        }
        String[] fs = url.split("/");
        String fileName = fs[fs.length-1];
        if (DemoUtil.switch_configurefile(parentFile,fileName)) {
            String apkPath = parentFile.getPath()+"/"+fileName;
            AppUtil.installApp(getApplication(), apkPath);
        } else {
            DownloadTask task = new DownloadTask.Builder(url, parentFile)
                    .setFilename("")
                    .setMinIntervalMillisCallbackProcess(1000)
                    .setPassIfAlreadyCompleted(false)
                    .build();
            StatusUtil.Status status= StatusUtil.getStatus(task);
            Log.e(TAG,"status:"+status.name());
            if (status == StatusUtil.Status.COMPLETED){
                //已经完成，跳转安装
                if (task.getFile()!=null){
                    AppUtil.installApp(getApplication(), task.getFile());
                }else{
                    String filename = OkDownload.with().breakpointStore().getResponseFilename(url);
                    if (!TextUtils.isEmpty(filename)){
                        File file =new File(parentFile, filename);
                        AppUtil.installApp(getApplication(), file);
                    }
                }
            }else if (status == StatusUtil.Status.PENDING ||status == StatusUtil.Status.RUNNING){
                //已经在下载中，不作处理
            }else{
                //取消当前所有任务
                OkDownload.with().downloadDispatcher().cancelAll();
                handler.removeCallbacksAndMessages(null);
                curRetryCount = MaxRetryCount;
                //下载本次任务
                downloadListener=new DownloadListener1() {
                    @Override
                    public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {

                    }

                    @Override
                    public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {

                    }

                    @Override
                    public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {

                    }

                    @Override
                    public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
                        callbackProgress(url,1,currentOffset,totalLength);
                        curRetryCount = MaxRetryCount;
                    }

                    @Override
                    public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
                        if (cause == EndCause.COMPLETED){
                            callbackProgress(url,2,0,0);
                            if (task.getFile()!=null && AppUtil.apkIsValid(getApplicationContext(),task.getFile().getAbsolutePath())){
                                AppUtil.installApp(getApplicationContext(),task.getFile());
                            }
                        }else if (cause == EndCause.ERROR){
                            if (realCause!=null){
                                realCause.printStackTrace();
                            }
                            processRetry(task);
                        }
                    }
                };
                task.enqueue(downloadListener);
            }
        }
    }

    /**
     * 回调下载进度给网页
     * @param apkUrl apk文件下载地址
     * @param state 状态 1：下载中，2：下载完成，3:下载失败
     * @param downloadedSize 已下载大小，字节数
     * @param totalSize 文件总大小 字节数
     */
    private void callbackProgress(String apkUrl,int state,long downloadedSize,long totalSize){
        webView.loadUrl(String.format(Locale.getDefault(),"javascript:onProgress('%s',%d,%d,%d)", apkUrl, state, downloadedSize,totalSize));
    }

    private void processRetry(final DownloadTask task){
        Log.e(TAG,"cur retryCount:"+curRetryCount);
        if (curRetryCount<1){
            Toast.makeText(getApplicationContext(),"下载失败，请稍后重试",Toast.LENGTH_LONG).show();
	    callbackProgress(task.getUrl(),3,0,0);
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!stopRetry){
                    curRetryCount--;
                    Log.e(TAG,"left retryCount:"+curRetryCount);
                    task.enqueue(downloadListener);
                }
            }
        },RetryInterval);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(webView.canGoBack()){
                webView.goBack();
            }else{
                activityExit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void activityExit(){
        stopRetry = true;
        handler.removeCallbacksAndMessages(null);
        OkDownload.with().downloadDispatcher().cancelAll();
        finish();
    }
    /**
     * 权限请求，android 10及以上判断是否能获取OAID，
     * Android 10 以下判断：android.permission.READ_PHONE_STATE，获取IMEI
     */
    private void checkAndRequestPermission(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
            //android Q 设备获取oaid, sdk请查看网址：http://www.msa-alliance.cn/col.jsp?id=120
            new TJOAIDUtil(new TJOAIDUtil.OAIDListener() {
                @Override
                public void oaidGetSuccess(String oaid) {
                    buildUrl(oaid);
                }
                @Override
                public void oaidGetFail(int errorCode, String errorMsg) {
                    showError(errorMsg);
                }
            }).getDeviceIds(getApplicationContext());
        }else{
            PermissionUtil.requestPermissions(this, 110 , new String[]{Manifest.permission.READ_PHONE_STATE}, new PermissionUtil.OnPermissionListener() {
                @Override
                public void onPermissionGranted() {
                    buildUrl(null);
                }

                @Override
                public void onPermissionDenied() {
                    PermissionUtil.showTipsDialog(H5ActivityOptDY.this);
                }
            });
        }
    }
    private void showError(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        activityExit();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void buildUrl(String oaid){
        try {
            //TODO Demo使用AndroidID作为userID，开发者对接时请使用自己应用的用户ID。
            // 在媒体开发者接入时，请传入自己应用的用户账号，用户完成任务时，会在回调接口中返回
            String userId = SharedPreferencesUtil.getStringValue(this, SharedPreConstants.merCode, "");
//          String userId= DeviceUtil.getAndroidID(getApplication());
//          String baseUrl = BuildConfig.appIntranet ? "http://www.l.xyou.cn:9191/" : "https://app.91taojin.com.cn/";
//          String baseUrl = "https://app.91taojin.com.cn/";
            String baseUrl = "https://h5.ads66.com/?";
//            String appId = "65";
//            String appKey = "6dd20c04be295ef26fcb39eef1efe27d";
//          String url = UrlUtil.buildUrl(getApplicationContext(),userId,oaid,baseUrl,appId,appKey);
            String url = DuoyouUtils.buildUrl(getApplicationContext(),userId,oaid,baseUrl, getIntent().getStringExtra("appid"),getIntent().getStringExtra("appkey"));
            Log.e("tag","url=="+url);
            webView.loadUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (picUtil != null){
            picUtil.onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
