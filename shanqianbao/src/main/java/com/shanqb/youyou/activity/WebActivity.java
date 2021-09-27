package com.shanqb.youyou.activity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Stack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.shanqb.youyou.R;
import com.shanqb.youyou.utils.CheckAppInstalledUtil;
import com.shanqb.youyou.utils.DownloadCompleteReceiver;
import com.shanqb.youyou.utils.LogUtils;
import com.shanqb.youyou.view.MyWebChromeClient;

public class WebActivity extends BaseActivity {
	private WebView webView;
	private View viewBack;
	private View viewLogo;
	private String url;
	private boolean isLoading = false;
	private boolean isLoadSuccess = false;
	private TextView titleBar;
	private String title;
	private String body;
	private Stack<String> urlStack;
	
	String cookies = "";
    SharedPreferences sp;

	public static final int CALL_SAHRE_FROM_JS = FIRST_VAL++;
	public static final int CALL_JS_CALLBACK = FIRST_VAL++;

	static ArrayList<String> TAB_URLS;
	static {
		TAB_URLS = new ArrayList<String>();
		TAB_URLS.add("http://192.168.1.5:8084/XWGame/front/index/home");
		TAB_URLS.add("http://192.168.1.5:8084/XWGame/front/myInfo/myInfo");
	}

	public static boolean isHomePage(String u) {
		return u != null && u.equals("http://192.168.1.5:8084/XWGame/front/index/home");
	}

	public static boolean isTabUrl(String u) {
		if (TAB_URLS == null) {
			return false;
		}
		for (String url : TAB_URLS) {
			if (url != null && url.equals(u)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 往URL栈中加入一个链接地址
	 * 
	 * @param url
	 */
	public void pushUrl(String url) {
		if (urlStack == null) {
			urlStack = new Stack<String>();
		}
		urlStack.push(url);

		debugWebStack();
	}

	public boolean isUrlInStack(String url) {
		if (urlStack == null || urlStack.size() == 0) {
			return false;
		}
		for (int i = 0; i < urlStack.size(); i++) {
			String urlInStack = urlStack.get(i);
			if (urlInStack.equals(url)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 依次从URL栈中取出若干个链接地址，直到与传入的url相同的链接被pop
	 */
	public void popUrl(String url) {
		if (urlStack == null) {
			return;
		}
		while (urlStack.size() > 0) {
			// 取出一个URL
			String urlInStack = urlStack.pop();
			if (urlInStack.equals(url)) {
				break;
			}
		}
	}

	/**
	 * 从URL栈中取出一个链接地址
	 */
	public String popUrl() {
		if (urlStack == null || urlStack.size() < 2) {
			return null;
		}

		// 记录被回退的界面
		lastBackUrl = urlStack.pop();
		return urlStack.peek();
	}

	/**
	 * webview是否能回退标志
	 */
	public boolean isHasBack() {
		return urlStack != null && urlStack.size() > 1;
	}

	/**
	 * 去URL栈的前一个页面加载到webview
	 */
	public void back() {
		String backurl = popUrl();
		if (!isTabUrl(backurl)) {
			viewBack.setVisibility(View.VISIBLE);
		} else {
			viewBack.setVisibility(View.GONE);
		}
		if (isHomePage(backurl)) {
			viewLogo.setVisibility(View.VISIBLE);
			titleBar.setVisibility(View.GONE);

		} else {
			viewLogo.setVisibility(View.GONE);
			titleBar.setVisibility(View.VISIBLE);
		}
		if (backurl != null) {
			webView.loadUrl(backurl);
		}
	}

	/** 上一次尝试回退的界面 */
	private String lastBackUrl = null;

	/**
	 * 在webview中加载一个地址
	 */
	public void loadUrl(String url) {
		//synCookies(this,url);
		if (url == null) {
			return;
		}
		if (isUrlInStack(url)) {
			popUrl(url);
		}
		pushUrl(url);

		webView.loadUrl(url);

		if (!isTabUrl(url)) {
			viewBack.setVisibility(View.VISIBLE);
		} else {
			viewBack.setVisibility(View.GONE);
		}
		if (isHomePage(url)) {
			viewLogo.setVisibility(View.VISIBLE);
			titleBar.setVisibility(View.GONE);

		} else {
			viewLogo.setVisibility(View.GONE);
			titleBar.setVisibility(View.VISIBLE);
		}

		LogUtils.debug("loadUrl, " + url);

	}

	public void debugWebStack() {
		// if (urlStack == null) {
		//
		// return;
		// }
		// StringBuffer str = new StringBuffer("URL栈中的链接\n");
		// for (int i = 0; i < urlStack.size(); i++) {
		// str.append("[" + i + "]" + urlStack.get(i) + "\n");
		// }
		// LogUtils.debug(str.toString());
	}

	protected void onNewIntent(Intent intent) {
		url = getIntent().getStringExtra("url");
		loadUrl(url);
	}

	public void findViews() {
		webView = (WebView) findViewById(R.id.webView);
		viewBack = findViewById(R.id.viewBack);
		viewLogo = findViewById(R.id.viewLogo);

		titleBar = (TextView) findViewById(R.id.title_bar_text);
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("aaa", MODE_PRIVATE);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_web);

		url = getIntent().getStringExtra("url");

		findViews();

		webView.setWebViewClient(webViewClient);
		webView.setWebChromeClient(chromeClient);

		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true);
		// webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		

		// if (Build.VERSION.SDK_INT >= 19) {
		// webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		// }
		//后来加的
//		CookieManager cookieManager = CookieManager.getInstance();
//		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//		{
//		    cookieManager.setAcceptThirdPartyCookies(webView,true);
//		} else {
//		    cookieManager.setAcceptCookie(true);
//		}
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
		}
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setLoadsImagesAutomatically(true);
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
	    String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
	    webView.getSettings().setAppCachePath(appCachePath);
	    webView.getSettings().setAllowFileAccess(true); 
	    webView.getSettings().setAppCacheEnabled(true);

		addJsMethod();
		
		// 通过如下设置，来启动外部浏览器打�?��载链�?
		webView.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				System.out.println(url);
				openUrl(url);
			}
		});
		if (url != null) {
			if (url.toLowerCase().startsWith("www.")) {
				url = "http://" + url;
			}
			loadUrl(url);
		}

		else if ((body = getIntent().getStringExtra("body")) != null) {
			String encoding = "UTF-8";
			webView.getSettings().setDefaultTextEncodingName(encoding);
			webView.loadDataWithBaseURL(null, body, "text/html", encoding, null);
		}

		else {
			finish();
			return;
		}

		title = getIntent().getStringExtra("title");
		if (title != null) {
			titleBar.setText(title);
		}

		viewBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isLoading) {
					isLoading = false;
					webView.stopLoading();
				}
				back();
			}
		});

	}
	
	/**
     * 同步一下cookie
     */
//    public void synCookies(Context context, String url) {
//        CookieSyncManager.createInstance(context);
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);
////        cookieManager.removeSessionCookie();//移除
//        cookieManager.setCookie(url, cookies);
//        CookieSyncManager.getInstance().sync();
//    }

	public void onClick(View v) {

	};
	
	MyWebChromeClient chromeClient = new MyWebChromeClient(this) {

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			if (title != null) {
				WebActivity.this.title = title;
				titleBar.setText(title);
			}
		}

	};

	WebViewClient webViewClient = new WebViewClient() {

		@Override
		public void onPageFinished(WebView view, String url) {
			isLoading = false;
			isLoadSuccess = true;

			LogUtils.debug("onPageFinished, url=" + url);
			super.onPageFinished(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

//			if (lastBackUrl != null && lastBackUrl.equals(url)) {
//				lastBackUrl = null;
//				back();
//				return true;
//			}

			lastBackUrl = null;
			//获取cookies
//            CookieManager cm = CookieManager.getInstance();
//            cookies = cm.getCookie(url);
			loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			WebActivity.this.url = url;
			isLoading = true;

			LogUtils.debug("onPageStarted, url=" + url);

		}

		@Override
		public void onTooManyRedirects(WebView view, Message cancelMsg,
				Message continueMsg) {
			super.onTooManyRedirects(view, cancelMsg, continueMsg);
		}

	};

	public void call(int callID, Object... args) {
		if (callID == CALL_JS_CALLBACK) {
			finish();
		}

	};

	class JsCallBack {
	}

	@SuppressLint("JavascriptInterface")
	public void addJsMethod() {
//		JsCallBack jsCallBack = new JsCallBack();
//		webView.addJavascriptInterface(jsCallBack, "jsCallBack");
		//别名必须设置为android
	    webView.addJavascriptInterface(this, "android");
	}
	
	@JavascriptInterface
	public String getIMEI(int slotId) {
        try {
            TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            if (slotId == 3) {
            	String deviceID = manager.getDeviceId();
            	return deviceID;
            } else {
            	 Method method = manager.getClass().getMethod("getImei", int.class);
                 String imei = (String) method.invoke(manager, slotId);
                 return imei;
            }
        } catch (Exception e) {
            return "";
        }
    }
	
	@JavascriptInterface
	public String getMEID() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = "";
        try {
            Method getMeid = tm.getClass().getDeclaredMethod("getMeid");
            deviceId = (String) getMeid.invoke(tm);
        } catch (Throwable e) {
            return "";
        }
        if (null == deviceId) {
            deviceId = "";
        }
        return deviceId;
    }
	
	@JavascriptInterface
	public static String getSystemVersion() {  
        return android.os.Build.VERSION.RELEASE;  
    } 
	
	/** 
     * 获取手机型号 
     * 
     * @return  手机型号 
     */  
	@JavascriptInterface
    public static String getSystemModel() {  
        return android.os.Build.MODEL;  
    }
	
	@JavascriptInterface
    public void CheckInstall(final String packageName) {
        //判断指定app是否已经安装
        final boolean isInstalled = CheckAppInstalledUtil.isInstalled(this, packageName);
        if (isInstalled) {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:CheckInstall_Return(1)");
                }
            });
        } else {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:CheckInstall_Return(0)");
                }
            });
        }
    }
	
	/**
     * 此方法实现打开指定app功能
     *
     * @param packageName
     */
    @JavascriptInterface
    public void OpenAPP(String packageName) {
    }
    /**
     * 此方法实现，外部浏览器打开指定url功能
     *
     * @param downurl
     */
    @JavascriptInterface
    public void Browser(String url) {
    	System.out.println(url);
//    	openUrl(url);
    	downloadBySystem(url,"", "apk");
    	DownloadCompleteReceiver receiver = new DownloadCompleteReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver, intentFilter);
    }
    
    /**
     * 此方法实现，打开qq客服
     *
     * @param downurl
     */
    @JavascriptInterface
    public void startQQConversation(String qq) {
        if (CheckAppInstalledUtil.isInstalled(this, "com.tencent.mobileqq")) {
        	this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qq)));
        } else {
        	Toast.makeText(this, "请检查QQ是否可用!",Toast.LENGTH_SHORT).show();
        }
    }

	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_UP) {
				if (isLoading) {
					isLoading = false;
					webView.stopLoading();
				}
				if (isHasBack()) {

					debugWebStack();

					back();

				} else {
					close();
				}
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	/** 两次点击返回键退出应用的间隔时间 */
	public static final long DOUBLE_TAP_TIME = 3000L;
	/** 上次点击返回键的时间 */
	private long lastTapBackTime;

	public void close() {
		long time = System.currentTimeMillis();
		if (time - lastTapBackTime > DOUBLE_TAP_TIME) {
			Toast.makeText(this,
					"再按一次退出" + getResources().getString(R.string.app_name),
					Toast.LENGTH_SHORT).show();
			lastTapBackTime = time;
		} else {
			finish();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == chromeClient.getRequestCodeFilePicker()) {
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					if (chromeClient.getFileUploadCallbackFirst() != null) {
						chromeClient.getFileUploadCallbackFirst()
								.onReceiveValue(data.getData());
						chromeClient.setFileUploadCallbackFirst(null);
					} else if (chromeClient.getFileUploadCallbackSecond() != null) {
						Uri[] dataUris;
						try {
							dataUris = new Uri[] { Uri.parse(data
									.getDataString()) };
						} catch (Exception e) {
							dataUris = null;
						}

						chromeClient.getFileUploadCallbackSecond()
								.onReceiveValue(dataUris);
						chromeClient.setFileUploadCallbackSecond(null);
					}
				}
			} else {
				if (chromeClient.getFileUploadCallbackFirst() != null) {
					chromeClient.getFileUploadCallbackFirst().onReceiveValue(
							null);
					chromeClient.setFileUploadCallbackFirst(null);
				} else if (chromeClient.getFileUploadCallbackSecond() != null) {
					chromeClient.getFileUploadCallbackSecond().onReceiveValue(
							null);
					chromeClient.setFileUploadCallbackSecond(null);
				}
			}
		}
	}

	@Override
	public void addAction() {
		addBackAction();
	}
}
