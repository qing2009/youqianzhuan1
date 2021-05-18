/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 */

package com.shanqb.qianrenzixun.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    /**
     * 连接超时时间, 默认30秒钟
     */
    private int connectTimeout = 30 * 1000;
    /**
     * 读取超时时间，默认30秒
     */
    private int readTimeout = 30 * 1000;
    /**
     * 代理对象
     */
    private Proxy mProxy = null;
    /**
     * 上下文对象
     */
    private Context mContext;

    /**
     * 请求及回应报文字符集
     */
    public String encoding = "UTF-8";

    /**
     * 请求是否取消标志
     */
    private boolean isCanceled = false;

    /**
     * Http请求对象
     */
    private HttpURLConnection httpURLConnection;

    /**
     * 请求序列号，标识第几次请求
     */
    private static int reqIndex = 0;

    /**
     * 取消一个请求
     */
    public void cancel() {

        isCanceled = true;
        try {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NetworkUtils(Context context, String encoding) {
        this.mContext = context;
        this.encoding = encoding;
        setDefaultHostnameVerifier();
    }

    public NetworkUtils(Context context) {
        this.mContext = context;
        setDefaultHostnameVerifier();
    }

    public NetworkUtils(Context context, DownloadListener downloadListener) {
        this.mContext = context;
        this.downloadListener = downloadListener;
    }

    /**
     * 设置代理, 暂不需要实现
     */
    public void detectProxy() {
        // ConnectivityManager cm = (ConnectivityManager)
        // mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        // NetworkInfo ni = cm.getActiveNetworkInfo();
        // if (ni != null && ni.isAvailable() && ni.getType() ==
        // ConnectivityManager.TYPE_WIFI) {
        //
        // String proxyHost = android.net.Proxy.getDefaultHost();
        // int port = android.net.Proxy.getDefaultPort();
        // if (proxyHost != null) {
        // final InetSocketAddress sa = new InetSocketAddress(proxyHost, port);
        // mProxy = new Proxy(Proxy.Type.HTTP, sa);
        // }
        // }
    }

    private void setDefaultHostnameVerifier() {
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    /**
     * 请求一个URL地址，并读取回应的字节数组
     * 
     * @param strUrl
     * @return
     */
    public byte[] sendAndGetByteArray(String strUrl) {
        if (strUrl == null || isCanceled) {
            return null;
        }
        detectProxy();

        try {
            URL url = new URL(strUrl);

            if (mProxy != null) {
                httpURLConnection = (HttpURLConnection) url
                        .openConnection(mProxy);
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            httpURLConnection.setConnectTimeout(connectTimeout);
            httpURLConnection.setReadTimeout(readTimeout);
            httpURLConnection.setDoInput(true);

            if (!isCanceled) {
                httpURLConnection.connect();
            }

            InputStream content = httpURLConnection.getInputStream();
            try {
                return Utils.readByteArrayFromStream(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cancel();
        }

        return null;
    }

    /**
     * 请求一个URL地址，发送请求报文，并得到回应报文
     * 
     * @param strReqData
     * @param strUrl
     * @param _encoding
     * @param isLog
     * @return
     */
    public String sendAndWaitResponse(String strReqData, String strUrl,
            String _encoding, boolean isLog) {
        HashMap<String, String> m = new HashMap<String, String>();
        if (_encoding == null) {
            _encoding = encoding;
        }
        // m.put("Content-type", "application/x-www-form-urlencoded;charset=" +
        // _encoding);
        m.put("Content-type", "text/json;charset=" + _encoding);
        return sendAndWaitResponse(strReqData, strUrl, _encoding, m, isLog);
    }

    public String sendAndWaitResponse(byte[] bytes, String strUrl,
            String _encoding, boolean isLog) {
        HashMap<String, String> m = new HashMap<String, String>();
        if (_encoding == null) {
            _encoding = encoding;
        }
        m.put("Content-type", "application/x-www-form-urlencoded;charset="
                + _encoding);
        // m.put("Content-type", "text/json;charset=" + _encoding);
        return sendAndWaitResponse(bytes, strUrl, _encoding, m, isLog);
    }

    /**
     * 
     * Title: sendPostReq<br/>
     * Description: 发送post请求给支付<br/>
     * 
     * @author lai_jj
     * @date 2015年1月7日下午3:39:47
     * 
     * @param url
     * @param data
     * @return
     */
    public String sendPostReq2Pay(String url, String data) {
        HttpsURLConnection hc;
        OutputStream out = null;
        InputStream in = null;
        String res = null;
        try {
            LogUtils.debug("\nHttpURLConnection");
            LogUtils.debug(data);
            hc = (HttpsURLConnection) new URL(url).openConnection();
            hc.setDoOutput(true);
            hc.setUseCaches(false);
            hc.setRequestMethod("POST");
            hc.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            hc.connect();
            out = hc.getOutputStream();
            out.write(("req_data=" + URLEncoder.encode(data, "UTF-8"))
                    .getBytes());
            if (HttpURLConnection.HTTP_OK == hc.getResponseCode()) {
                in = hc.getInputStream();
                res = new String(readMessageAndClose(in), "UTF-8");
                LogUtils.debug(res);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return res;
    }

    /**
     * 
     * Title: readMessageAndClose<br/>
     * Description: 做转码<br/>
     * 
     * @author lai_jj
     * @date 2015年1月7日下午3:43:25
     * 
     * @param in
     * @return
     * @throws IOException
     */
    private static byte[] readMessageAndClose(InputStream in)
            throws IOException {
        byte[] buf = new byte[1024];
        byte[] tmpBuf;
        int onceread = 0, offset = 0;
        try {
            while (onceread >= 0) {
                offset += onceread;
                if (offset >= buf.length) {
                    tmpBuf = new byte[buf.length + 1024];
                    System.arraycopy(buf, 0, tmpBuf, 0, buf.length);
                    buf = tmpBuf;
                    tmpBuf = null;
                }
                onceread = in.read(buf, offset, buf.length - offset);
            }
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                }
                in = null;
            }

            tmpBuf = new byte[offset];
            System.arraycopy(buf, 0, tmpBuf, 0, offset);
            buf = tmpBuf;
            tmpBuf = null;
        }
        return buf;
    }

    /**
     * 
     * 请求一个URL地址，发送请求报文，并得到回应报文
     * 
     * @param strReqData
     * @param strUrl
     * @param _encoding
     * @param httpHeads
     * @param isLog
     * @return
     */
    public String sendAndWaitResponse(byte[] bytes, String strUrl,
            String _encoding, Map<String, String> httpHeads, boolean isLog) {

        int _reqIndex = ++reqIndex;
        if (_encoding == null) {
            _encoding = this.encoding;
        }
        if (isLog) {
            LogUtils.debug("\r\n文件上传至服务器地址[" + _reqIndex + "]:" + strUrl);
        }
        detectProxy();

        String strResponse = null;

        try {
            URL url = new URL(strUrl);

            if (mProxy != null) {
                httpURLConnection = (HttpURLConnection) url
                        .openConnection(mProxy);
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            httpURLConnection.setConnectTimeout(connectTimeout);
            httpURLConnection.setReadTimeout(readTimeout);
            httpURLConnection.setDoInput(true);
            if (httpHeads != null) {
                for (String key : httpHeads.keySet()) {
                    httpURLConnection.addRequestProperty(key,
                            httpHeads.get(key));
                }
            }

            if (!isCanceled && bytes != null) {
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(bytes);
                os.flush();
            }
            if (!isCanceled) {
                InputStream content = httpURLConnection.getInputStream();
                strResponse = new String(
                        Utils.readByteArrayFromStream(content), _encoding);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cancel();
        }

        if (isLog) {
            LogUtils.debug("回应数据[" + _reqIndex + "]:" + strResponse + "\r\n");
        }
        return strResponse;
    }

    /**
     * 
     * 请求一个URL地址，发送请求报文，并得到回应报文
     * 
     * @param strReqData
     * @param strUrl
     * @param _encoding
     * @param httpHeads
     * @param isLog
     * @return
     */
    public String sendAndWaitResponse(String strReqData, String strUrl,
            String _encoding, Map<String, String> httpHeads, boolean isLog) {

        int _reqIndex = ++reqIndex;
        if (_encoding == null) {
            _encoding = this.encoding;
        }
        if (isLog) {
            LogUtils.debug("\r\n请求地址[" + _reqIndex + "]:" + strUrl);
            LogUtils.debug("\r\n请求参数[" + _reqIndex + "]:" + strReqData);
        }
        detectProxy();

        String strResponse = null;

        try {
            URL url = new URL(strUrl);

            if (mProxy != null) {
                httpURLConnection = (HttpURLConnection) url
                        .openConnection(mProxy);
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            httpURLConnection.setConnectTimeout(connectTimeout);
            httpURLConnection.setReadTimeout(readTimeout);
            httpURLConnection.setDoInput(true);
            if (httpHeads != null) {
                for (String key : httpHeads.keySet()) {
                    httpURLConnection.addRequestProperty(key,
                            httpHeads.get(key));
                }
            }

            if (!isCanceled && strReqData != null) {
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(strReqData.getBytes(_encoding));
                os.flush();
            }
            if (!isCanceled) {
                InputStream content = httpURLConnection.getInputStream();
                strResponse = new String(
                        Utils.readByteArrayFromStream(content), _encoding);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cancel();
        }

        if (isLog) {
            LogUtils.debug("回应数据[" + _reqIndex + "]:" + strResponse + "\r\n");
        }
        return strResponse;
    }

    public boolean urlDownloadToFile(Context context, String strurl, String path) {
        boolean bRet = false;

        detectProxy();

        try {
            URL url = new URL(strurl);
            if (mProxy != null) {
                httpURLConnection = (HttpURLConnection) url
                        .openConnection(mProxy);
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            httpURLConnection.setConnectTimeout(connectTimeout);
            httpURLConnection.setReadTimeout(readTimeout);
            httpURLConnection.setDoInput(true);
            if (!isCanceled) {

                File file = new File(path);
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);

                byte[] temp = new byte[1024];
                int i = 0;

                InputStream is = httpURLConnection.getInputStream();
                while ((i = is.read(temp)) > 0) {
                    if (isCanceled) {
                        break;
                    }
                    fos.write(temp, 0, i);
                }

                fos.close();
                is.close();

                bRet = true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cancel();
        }

        return bRet;
    }

    public NetworkUtils downloadFiles(String urlStr) {

        detectProxy();

        try {
            URL url = new URL(urlStr);
            if (mProxy != null) {
                httpURLConnection = (HttpURLConnection) url
                        .openConnection(mProxy);
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            if (downloadListener != null && !isCanceled) {
                downloadListener.setMaxDownloadCount(httpURLConnection
                        .getContentLength());
            }

            httpURLConnection.setConnectTimeout(connectTimeout);
            httpURLConnection.setReadTimeout(readTimeout);

            InputStream is = httpURLConnection.getInputStream();

            if (downloadListener != null && !isCanceled) {
                downloadListener.downloadStarted();
            }

            byte[] temp = new byte[1024 * 8];
            int i = 0;
            while ((i = is.read(temp)) > 0 && !isCanceled) {

                if (downloadListener != null && !isCanceled) {
                    downloadListener.download(temp, 0, i);
                }
            }

            if (downloadListener != null && !isCanceled) {
                downloadListener.downloadfinished();
            }
            is.close();
            httpURLConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();

            if (downloadListener != null && !isCanceled) {
                downloadListener.downloadError(e);
            }
        } finally {
            cancel();
        }

        return this;
    }

    private DownloadListener downloadListener;

    /**
     * 文件下载监听器适配器
     * 
     * @author liu_kf
     * 
     */
    public static class DownloadAdapter implements DownloadListener {
        public void download(int ch) {
        }

        public void download(byte[] buffer, int start, int length) {
        }

        public void downloadError(Exception e) {
        }

        public void downloadStarted() {
        }

        public void downloadfinished() {
        }

        public void setMaxDownloadCount(int maxCount) {
        }
    }

    /**
     * 文件下载监听器
     * 
     * @author liu_kf
     * 
     */
    public static interface DownloadListener {
        /**
         * 下载到一个字节
         * 
         * @param ch
         */
        public void download(int ch);

        /**
         * 下载到一个字节数组
         * 
         * @param buffer
         * @param start
         * @param length
         */
        public void download(byte[] buffer, int start, int length);

        /**
         * 设置本次下载的字节数
         * 
         * @param maxCount
         */
        public void setMaxDownloadCount(int maxCount);

        /**
         * 通知现在完成
         */
        public void downloadfinished();

        /**
         * 下载开始
         */
        public void downloadStarted();

        /**
         * 下载发送错误
         */
        public void downloadError(Exception e);
    }



    /**
     * check network status
     *
     * @param context
     * @return
     */
    public static boolean checkNetworkConnectionState(Context context) {
        boolean flag = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            connectivityManager = null;

            if (networkInfo != null) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return flag = true;
                    }
                }
            }
        }
        return flag;
    }
}
