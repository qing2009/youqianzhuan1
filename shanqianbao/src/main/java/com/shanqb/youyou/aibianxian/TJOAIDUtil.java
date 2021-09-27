package com.shanqb.youyou.aibianxian;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.bun.miitmdid.core.ErrorCode;
import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.miitmdid.interfaces.IIdentifierListener;
import com.bun.miitmdid.interfaces.IdSupplier;

//10.0.25版本
public class TJOAIDUtil implements IIdentifierListener {
    //设备不支持
    public static final int OAID_STATE_NOTSUPPORT = 0;
    //OAID集成配置文件错误
    public static final int OAID_STATE_CONFIGFILE = 1;
    //OAID调用错误
    public static final int OAID_STATE_CALL_ERROR = 2;
    //OAID 获取超时
    public static final int OAID_STATE_TIMEOUT = 3;
    //发生异常
    public static final int OAID_STATE_OCCUR_EXCEPTION = 4;

    private OAIDListener listener;
    private Handler handler;
    private boolean isBack;
    private final Object lock = new Object();

    public TJOAIDUtil(OAIDListener callback) {
        listener = callback;
        handler = new Handler(Looper.getMainLooper());
    }

    public void getDeviceIds(Context context) {
        isBack = false;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                oaidGetFail(OAID_STATE_TIMEOUT,"OAID获取超时,请稍后重试");
            }
        },3000);
        try {
            int state= MdidSdkHelper.InitSdk(context,true,this);
            switch (state){
                case ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT:
                case ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT:
                    oaidGetFail(OAID_STATE_NOTSUPPORT,"当前设备暂不支持（原因：厂商不支持移动安全联盟获取OAID）");
                    break;
                case ErrorCode.INIT_ERROR_LOAD_CONFIGFILE:
                    oaidGetFail(OAID_STATE_CONFIGFILE,"OAID加载配置文件失败");
                    break;
                case ErrorCode.INIT_HELPER_CALL_ERROR:
                    oaidGetFail(OAID_STATE_CALL_ERROR,"OAID接口调用失败");
                    break;
                case ErrorCode.INIT_ERROR_RESULT_DELAY:
                    break;
            }
        }catch (Exception e){
            oaidGetFail(OAID_STATE_OCCUR_EXCEPTION,"OAID获取发生异常错误");
        }
    }
    private void oaidGetSuccess(final String oaid){
        if (isBack) return;
        synchronized (lock){
            if (!isBack){
                isBack = true;
                if (listener!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.oaidGetSuccess(oaid);
                        }
                    });
                }
            }
        }
    }
    private void oaidGetFail(final int errorCode, final String errorMsg){
        if (isBack) return;
        synchronized (lock){
            if (!isBack){
                isBack = true;
                if (listener!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.oaidGetFail(errorCode,errorMsg);
                        }
                    });
                }
            }
        }
    }
    @Override
    public void OnSupport(final boolean isSupport, final IdSupplier _supplier) {
        if (isSupport){
            if (_supplier!=null){
                String oaid = _supplier.getOAID();
                oaidGetSuccess(oaid);
            }else{
                oaidGetSuccess("");
            }
        }else{
            oaidGetFail(OAID_STATE_NOTSUPPORT,"设备不支持获取OAID");
        }
    }
    public interface OAIDListener{
        void oaidGetSuccess(String oaid);
        void oaidGetFail(int errorCode,String errorMsg);
    }
}

