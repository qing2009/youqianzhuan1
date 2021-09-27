package com.shanqb.youyou.aibianxian;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.shanqb.youyou.R;

/**
 * 淘金H5 Demo
 */
public class H5Activity extends Activity {
    private static final String TAG = "TJH5";
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        init();
        webView.loadUrl(getIntent().getStringExtra("url"));
    }
    private void init(){
        webView = findViewById(R.id.webView);
        initWebSetting();
        setWebViewClient();
        setWebDownloadListener();;
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
        finish();
    }
    private void showError(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        activityExit();
    }
}
