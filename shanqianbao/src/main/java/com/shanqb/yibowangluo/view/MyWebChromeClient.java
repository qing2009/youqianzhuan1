package com.shanqb.yibowangluo.view;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.MissingResourceException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

public class MyWebChromeClient extends WebChromeClient {

	 private Activity mActivity;
	 	//相册相关的webView配置
	    private ValueCallback<Uri> mUploadMessage;
	    public ValueCallback<Uri[]> uploadMessage;
	    public static final int REQUEST_SELECT_FILE = 100;
	    private final static int FILECHOOSER_RESULTCODE = 2;
	    
	    public MyWebChromeClient(Activity activity) {
	        this.mActivity = activity;
	        this.mLanguageIso3 = getLanguageIso3();
	    }

	    @Override
	    public void onCloseWindow(WebView window) {
	        super.onCloseWindow(window);
	    }

	    @Override
	    public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture,
	                                  Message resultMsg) {
	        return super.onCreateWindow(view, dialog, userGesture, resultMsg);
	    }

//	    /**
//	     * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
//	     */
//	    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
//	        final AlertDialog dlg = new AlertDialog.Builder(view.getContext()).create();
//	        dlg.setCanceledOnTouchOutside(false);
//	        dlg.setCancelable(false);
//	        dlg.show();
//	        Window window = dlg.getWindow();
//	        window.setContentView(R.layout.wobaifu_messgae_alert);
//	        TextView textViewMessage = (TextView) window.findViewById(R.id.message);
//	        LinearLayout positiveButton = (LinearLayout) window.findViewById(R.id.positiveButton);
//
//	        textViewMessage.setText(message);
//	        positiveButton.setOnClickListener(new android.view.View.OnClickListener() {
//	            @Override
//	            public void onClick(View v) {
//	                result.confirm();
//	                dlg.dismiss();
//	            }
//	        });
//
//	        return true;
//	        // return super.onJsAlert(view, url, message, result);
//	    }

	    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
	        return super.onJsBeforeUnload(view, url, message, result);
	    }

//	    /**
//	     * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
//	     */
//	    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
//	        final AlertDialog dlg = new AlertDialog.Builder(view.getContext()).create();
//	        dlg.setCanceledOnTouchOutside(false);
//	        dlg.setCancelable(false);
//	        dlg.show();
//	        Window window = dlg.getWindow();
//	        window.setContentView(R.layout.wobaifu_messgae_confirm);
//	        TextView textViewMessage = (TextView) window.findViewById(R.id.message);
//	        LinearLayout positiveButton = (LinearLayout) window.findViewById(R.id.positiveButton);
//	        LinearLayout negativeButton = (LinearLayout) window.findViewById(R.id.negativeButton);
//
//	        textViewMessage.setText(message);
//	        negativeButton.setOnClickListener(new android.view.View.OnClickListener() {
//	            @Override
//	            public void onClick(View v) {
//	                dlg.dismiss();
//	                result.confirm();
//	            }
//	        });
//	        positiveButton.setOnClickListener(new android.view.View.OnClickListener() {
//	            @Override
//	            public void onClick(View v) {
//	                dlg.dismiss();
//	                result.cancel();
//	            }
//	        });
//
//	        // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
//	        dlg.setOnKeyListener(new OnKeyListener() {
//	            @Override
//	            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//	                Log.v("onJsConfirm", "keyCode==" + keyCode + "event=" + event);
//	                return true;
//	            }
//	        });
//	        // 禁止响应按back键的事件
//	        // builder.setCancelable(false);
//	        // AlertDialog dialog = builder.create();
//	        // dialog.show();
//	        return true;
//	        // return super.onJsConfirm(view, url, message, result);
//	    }

	    /**
	     * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
	     * window.prompt('请输入您的域名地址', '618119.com');
	     */
	    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
	                              final JsPromptResult result) {
	        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
	        builder.setTitle("对话框").setMessage(message);

	        final EditText et = new EditText(view.getContext());
	        et.setSingleLine();
	        et.setText(defaultValue);
	        builder.setView(et).setPositiveButton("确定", new OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                result.confirm(et.getText().toString());
	            }

	        }).setNeutralButton("取消", new OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                result.cancel();
	            }
	        });

	        // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
	        builder.setOnKeyListener(new OnKeyListener() {
	            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
	                Log.v("onJsPrompt", "keyCode==" + keyCode + "event=" + event);
	                return true;
	            }
	        });

	        // 禁止响应按back键的事件
	        // builder.setCancelable(false);
	        AlertDialog dialog = builder.create();
	        dialog.show();
	        return true;
	        // return super.onJsPrompt(view, url, message, defaultValue,
	        // result);
	    }

	    @Override
	    public void onProgressChanged(WebView view, int newProgress) {
	        super.onProgressChanged(view, newProgress);
	    }

	    @Override
	    public void onReceivedIcon(WebView view, Bitmap icon) {
	        super.onReceivedIcon(view, icon);
	    }

	    @Override
	    public void onReceivedTitle(WebView view, String title) {
	        super.onReceivedTitle(view, title);
	    }

	    @Override
	    public void onRequestFocus(WebView view) {
	        super.onRequestFocus(view);
	    }


	    // file upload callback (Android 2.2 (API level 8) -- Android 2.3 (API level 10)) (hidden method)
//	    @SuppressWarnings("unused")
//	    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//	        openFileChooser(uploadMsg, null);
//	    }
//
//	    // file upload callback (Android 3.0 (API level 11) -- Android 4.0 (API level 15)) (hidden method)
//	    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
//	        openFileChooser(uploadMsg, acceptType, null);
//	    }
//
//	    // file upload callback (Android 4.1 (API level 16) -- Android 4.3 (API level 18)) (hidden method)
//	    @SuppressWarnings("unused")
//	    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//	        openFileInput(uploadMsg, null);
//	    }

	    // file upload callback (Android 5.0 (API level 21) -- current) (public method)
//	    @SuppressWarnings("all")
//	    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
//	        openFileInput(null, filePathCallback);
//	        return true;
//	    }
	    
	 // For 3.0+ Devices (Start)
        // onActivityResult attached before constructor
        protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
//            startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
        }
        
        // For Lollipop 5.0+ Devices
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }
            uploadMessage = filePathCallback;
            Intent intent = fileChooserParams.createIntent();
            try {
//                startActivityForResult(intent, REQUEST_SELECT_FILE);
            } catch (ActivityNotFoundException e) {
                uploadMessage = null;
                Toast.makeText(mActivity, "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
        //For Android 4.1 only
        protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
//            startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
        }
        protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
//            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }

	    /** File upload callback for platform versions prior to Android 5.0 */
	    protected ValueCallback<Uri> mFileUploadCallbackFirst;
	    /** File upload callback for Android 5.0+ */
	    protected ValueCallback<Uri[]> mFileUploadCallbackSecond;
	    protected String mUploadableFileTypes = "*/*";
	    protected static final int REQUEST_CODE_FILE_PICKER = 51426;
	    protected int mRequestCodeFilePicker = REQUEST_CODE_FILE_PICKER;

	    @SuppressLint("NewApi")
	    protected void openFileInput(final ValueCallback<Uri> fileUploadCallbackFirst, final ValueCallback<Uri[]> fileUploadCallbackSecond) {
	        if (mFileUploadCallbackFirst != null) {
	            mFileUploadCallbackFirst.onReceiveValue(null);
	        }
	        mFileUploadCallbackFirst = fileUploadCallbackFirst;

	        if (mFileUploadCallbackSecond != null) {
	            mFileUploadCallbackSecond.onReceiveValue(null);
	        }
	        mFileUploadCallbackSecond = fileUploadCallbackSecond;

	        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	        i.addCategory(Intent.CATEGORY_OPENABLE);
	        i.setType(mUploadableFileTypes);

	        if (mActivity != null && mActivity != null) {
	            mActivity.startActivityForResult(Intent.createChooser(i, getFileUploadPromptLabel()), mRequestCodeFilePicker);
	        }
	    }

	    protected String mLanguageIso3;
	    protected static final String LANGUAGE_DEFAULT_ISO3 = "eng";

	    protected static String getLanguageIso3() {
	        try {
	            return Locale.getDefault().getISO3Language().toLowerCase(Locale.US);
	        } catch (MissingResourceException e) {
	            return LANGUAGE_DEFAULT_ISO3;
	        }
	    }

	    /** Provides localizations for the 25 most widely spoken languages that have a ISO 639-2/T code */
	    protected String getFileUploadPromptLabel() {
	        try {
	            if (mLanguageIso3.equals("zho")) return decodeBase64("6YCJ5oup5LiA5Liq5paH5Lu2");
	            else if (mLanguageIso3.equals("spa")) return decodeBase64("RWxpamEgdW4gYXJjaGl2bw==");
	            else if (mLanguageIso3.equals("hin"))
	                return decodeBase64("4KSP4KSVIOCkq+CkvOCkvuCkh+CksiDgpJrgpYHgpKjgpYfgpII=");
	            else if (mLanguageIso3.equals("ben"))
	                return decodeBase64("4KaP4KaV4Kaf4Ka/IOCmq+CmvuCmh+CmsiDgpqjgpr/gprDgp43gpqzgpr7gpprgpqg=");
	            else if (mLanguageIso3.equals("ara"))
	                return decodeBase64("2KfYrtiq2YrYp9ixINmF2YTZgSDZiNin2K3Yrw==");
	            else if (mLanguageIso3.equals("por")) return decodeBase64("RXNjb2xoYSB1bSBhcnF1aXZv");
	            else if (mLanguageIso3.equals("rus"))
	                return decodeBase64("0JLRi9Cx0LXRgNC40YLQtSDQvtC00LjQvSDRhNCw0LnQuw==");
	            else if (mLanguageIso3.equals("jpn"))
	                return decodeBase64("MeODleOCoeOCpOODq+OCkumBuOaKnuOBl+OBpuOBj+OBoOOBleOBhA==");
	            else if (mLanguageIso3.equals("pan"))
	                return decodeBase64("4KiH4Kmx4KiVIOCoq+CovuCoh+CosiDgqJrgqYHgqKPgqYs=");
	            else if (mLanguageIso3.equals("deu")) return decodeBase64("V8OkaGxlIGVpbmUgRGF0ZWk=");
	            else if (mLanguageIso3.equals("jav")) return decodeBase64("UGlsaWggc2lqaSBiZXJrYXM=");
	            else if (mLanguageIso3.equals("msa")) return decodeBase64("UGlsaWggc2F0dSBmYWls");
	            else if (mLanguageIso3.equals("tel"))
	                return decodeBase64("4LCS4LCVIOCwq+CxhuCxluCwsuCxjeCwqOCxgSDgsI7gsILgsJrgsYHgsJXgsYvgsILgsKHgsL8=");
	            else if (mLanguageIso3.equals("vie"))
	                return decodeBase64("Q2jhu41uIG3hu5l0IHThuq1wIHRpbg==");
	            else if (mLanguageIso3.equals("kor"))
	                return decodeBase64("7ZWY64KY7J2YIO2MjOydvOydhCDshKDtg50=");
	            else if (mLanguageIso3.equals("fra"))
	                return decodeBase64("Q2hvaXNpc3NleiB1biBmaWNoaWVy");
	            else if (mLanguageIso3.equals("mar"))
	                return decodeBase64("4KSr4KS+4KSH4KSyIOCkqOCkv+CkteCkoeCkvg==");
	            else if (mLanguageIso3.equals("tam"))
	                return decodeBase64("4K6S4K6w4K+BIOCuleCvh+CuvuCuquCvjeCuquCviCDgrqTgr4fgrrDgr43grrXgr4E=");
	            else if (mLanguageIso3.equals("urd"))
	                return decodeBase64("2KfbjNqpINmB2KfYptmEINmF24zauiDYs9uSINin2YbYqtiu2KfYqCDaqdix24zaug==");
	            else if (mLanguageIso3.equals("fas"))
	                return decodeBase64("2LHYpyDYp9mG2KrYrtin2Kgg2qnZhtuM2K8g24zaqSDZgdin24zZhA==");
	            else if (mLanguageIso3.equals("tur")) return decodeBase64("QmlyIGRvc3lhIHNlw6dpbg==");
	            else if (mLanguageIso3.equals("ita")) return decodeBase64("U2NlZ2xpIHVuIGZpbGU=");
	            else if (mLanguageIso3.equals("tha"))
	                return decodeBase64("4LmA4Lil4Li34Lit4LiB4LmE4Lif4Lil4LmM4Lir4LiZ4Li24LmI4LiH");
	            else if (mLanguageIso3.equals("guj"))
	                return decodeBase64("4KqP4KqVIOCqq+CqvuCqh+CqsuCqqOCrhyDgqqrgqrjgqoLgqqY=");
	        } catch (Exception e) {
	        }

	        // return English translation by default
	        return "Choose a file";
	    }

	    protected static final String CHARSET_DEFAULT = "UTF-8";

	    protected static String decodeBase64(final String base64) throws IllegalArgumentException, UnsupportedEncodingException {
	        final byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
	        return new String(bytes, CHARSET_DEFAULT);
	    }

	    public ValueCallback<Uri> getFileUploadCallbackFirst() {
	        return mFileUploadCallbackFirst;
	    }

	    public ValueCallback<Uri[]> getFileUploadCallbackSecond() {
	        return mFileUploadCallbackSecond;
	    }

	    public int getRequestCodeFilePicker() {
	        return mRequestCodeFilePicker;
	    }

	    public void setFileUploadCallbackFirst(ValueCallback<Uri> fileUploadCallbackFirst) {
	        mFileUploadCallbackFirst = fileUploadCallbackFirst;
	    }

	    public void setFileUploadCallbackSecond(ValueCallback<Uri[]> fileUploadCallbackSecond) {
	        mFileUploadCallbackSecond = fileUploadCallbackSecond;
	    }
	}
