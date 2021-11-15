package com.shanqb.myTool.activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shanqb.myTool.R;
import com.shanqb.myTool.utils.DownloadCompleteReceiver;
import com.shanqb.myTool.utils.LogUtils;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends Activity implements OnClickListener

{
	public int CALL_CANCEL_PROGRESS = 0;
	public int CALL_ONCLICK_PROGRESS = 0;
	public String errorPageMsg = null;
	public boolean isCanCancel = false;
	public boolean isShowLoading = false;
	public boolean isShowDialog = false;
	public static int FIRST_VAL = 100;
	private static final int WHAT_SHOW_PROGRESS_DIALOG = FIRST_VAL++;
	private static final int WHAT_SHOW_FULL_PROGRESS_DIALOG = FIRST_VAL++;
	private static final int WHAT_NET_ERROR_PROGRESS_DIALOG = FIRST_VAL++;
	private static final int WHAT_ERROR_PROGRESS_DIALOG = FIRST_VAL++;
	private static final int WHAT_NODATA_PROGRESS_DIALOG = FIRST_VAL++;
	private static final int WHAT_CANCEL_DIALOG = FIRST_VAL++;
	private static final int WHAT_CALL_FUNCTION = FIRST_VAL++;
	private static final int WHAT_SHOW_TOAST_TEXT = FIRST_VAL++;

	private LinearLayout linearShowLoading;
	private LinearLayout linearFullLoading;
	private LinearLayout linearNetErrorPage;
	private LinearLayout linearNoData;
	private LinearLayout linearErrorData;
	private TextView textViewErrorData;
	private ImageView spaceshipImage;
	private ImageView spaceshipImage1;

	protected boolean isLogin = false;

	/**
	 * 启动外部浏览器打开一个链接
	 * 
	 * @param url
	 *            需要打开的URL地址
	 */
	public void openUrl(String url) {
		LogUtils.debug("=========="+url);
		if (url == null) {
			return;
		}
		try {
//			Uri uri = Uri.parse(url);
//			Intent intent = new Intent(Intent.ACTION_VIEW, uri)
//					.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			startActivity(intent);
			downloadBySystem(url,"", "apk");
	    	DownloadCompleteReceiver receiver = new DownloadCompleteReceiver();
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
	        registerReceiver(receiver, intentFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downloadBySystem(String url, String contentDisposition, String mimeType) {
        // 指定下载地址
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 允许媒体扫描，根据下载的文件类型被加入相册、音乐等媒体库
        request.allowScanningByMediaScanner();
        // 设置通知的显示类型，下载进行时和完成后显示通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 设置通知栏的标题，如果不设置，默认使用文件名
//        request.setTitle("This is title");
        // 设置通知栏的描述
//        request.setDescription("This is description");
        // 允许在计费流量下下载
        request.setAllowedOverMetered(false);
        // 允许该记录在下载管理界面可见
        request.setVisibleInDownloadsUi(true);
        // 允许漫游时下载
        request.setAllowedOverRoaming(true);
        // 允许下载的网路类型
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        // 设置下载文件保存的路径和文件名
        String fileName  = URLUtil.guessFileName(url, contentDisposition, mimeType);
        request.setTitle(fileName);
        LogUtils.debug("fileName:{"+fileName+"}");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
//        另外可选一下方法，自定义下载路径
//        request.setDestinationUri()
//        request.setDestinationInExternalFilesDir()
        final DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        // 添加一个下载任务
        long downloadId = downloadManager.enqueue(request);
        LogUtils.debug("downloadId:{"+downloadId+"}");
    }

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(getClass().getSimpleName());
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(getClass().getSimpleName());
		MobclickAgent.onResume(this);
	}

	/**
	 * 关闭软键盘
	 */
	public static void closeKeyboard(Activity activity) {
		View view = activity.getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	// 显示小菊花
	public void showProgressDialog(boolean isCancel, int callId) {
		isCanCancel = isCancel;
		CALL_CANCEL_PROGRESS = callId;
		isShowDialog = true;
		isShowLoading = true;
		sendMessageToHanler(WHAT_SHOW_PROGRESS_DIALOG);
	}

	// 显示大菊花
	public void showFullProgressDialog(boolean isCancel, int callId) {
		isCanCancel = isCancel;
		CALL_CANCEL_PROGRESS = callId;
		isShowDialog = true;
		isShowLoading = true;
		sendMessageToHanler(WHAT_SHOW_FULL_PROGRESS_DIALOG);
	}

	// 显示无网络页面
	public void showNetErrorProgressDialog(boolean isCancel, int cancelId, int onClickId) {
		isCanCancel = isCancel;
		CALL_CANCEL_PROGRESS = cancelId;
		CALL_ONCLICK_PROGRESS = onClickId;
		isShowDialog = true;
		sendMessageToHanler(WHAT_NET_ERROR_PROGRESS_DIALOG);
	}

	// 显示错误
	public void showErrorProgressDialog(boolean isCancel, int cancelId, int onClickId, String msg) {
		isCanCancel = isCancel;
		CALL_CANCEL_PROGRESS = cancelId;
		CALL_ONCLICK_PROGRESS = onClickId;
		errorPageMsg = msg;
		isShowDialog = true;
		sendMessageToHanler(WHAT_ERROR_PROGRESS_DIALOG);
	}

	// 显示无数据
	public void showNoDataProgressDialog(boolean isCancel, int cancelId, int onClickId) {
		isCanCancel = isCancel;
		CALL_CANCEL_PROGRESS = cancelId;
		CALL_ONCLICK_PROGRESS = onClickId;
		isShowDialog = true;
		sendMessageToHanler(WHAT_NODATA_PROGRESS_DIALOG);
	}

	// 关闭菊花
	public void cancelLoadingDialog() {
		if (isShowDialog) {
			isShowLoading = false;
			isShowDialog = false;
			isCanCancel = false;
			// CALL_CANCEL_PROGRESS = 0;
			// CALL_ONCLICK_PROGRESS = 0;
			// errorPageMsg = null;
			sendMessageToHanler(WHAT_CANCEL_DIALOG);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			// 1. 是不是在显示自定义组件
			if (isShowLoading) {
				// 2. 是否可以取消自定义组件
				if (isCanCancel) {
					if (event.getAction() == KeyEvent.ACTION_UP) {
						runCallFunctionInHandler(CALL_CANCEL_PROGRESS);
					}
				}
				else {}
				return true;
			}
			else {
				return super.dispatchKeyEvent(event);
			}

		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		cancelLoadingDialog();
	}

	protected Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == WHAT_CALL_FUNCTION) {
				if (msg.obj == null) {
					call(msg.arg1);
				}
				else {
					call(msg.arg1, (Object[]) msg.obj);
				}
			}
		}
	};

	public void showToastText(Object text) {
		sendMessageToHanler(WHAT_SHOW_TOAST_TEXT, text + "");
	}

	public abstract void call(int id, Object... args);

	/**
	 * 在UI线程中运行call函数，一般call函数由子类复写，callID将作为call函数的参数
	 * 
	 * @param callID
	 *            在UI线程中运行call函数，一般call函数由子类复写，id将作为call函数的参数传入
	 * 
	 * @param args
	 */
	public void runCallFunctionInHandler(int callID, Object... args) {
		sendMessageToHanler(WHAT_CALL_FUNCTION, args, callID);
	}

	// 消息发送函数
	public void sendMessageToHanler(int what, int arg1) {
		Message message = handler.obtainMessage();
		message.what = what;
		message.arg1 = arg1;
		message.sendToTarget();
	}

	// 消息发送函数
	public void sendMessageToHanler(int what) {
		Message message = handler.obtainMessage();
		message.what = what;
		message.sendToTarget();
	}

	// 消息发送函数
	public void sendMessageToHanler(int what, Object obj) {
		Message message = handler.obtainMessage();
		message.what = what;
		message.obj = obj;
		message.sendToTarget();
	}

	// 消息发送函数
	public void sendMessageToHanler(int what, Object obj, int arg1) {
		Message message = handler.obtainMessage();
		message.what = what;
		message.obj = obj;
		message.arg1 = arg1;
		message.sendToTarget();
	}

	protected boolean isOpenLock = true;

	public String getRunningActivityName() {
		String contextString = this.toString();
		return contextString.substring(contextString.lastIndexOf(".") + 1,
				contextString.indexOf("@"));
	}

	public abstract void findViews();

	public abstract void addAction();

	/** 如果需要响应viewBack的返回事件，则可以调用这个函数 */
	public void addBackAction() {
		View viewBack = findViewById(R.id.viewBack);
		if (viewBack != null) {
			viewBack.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

}
