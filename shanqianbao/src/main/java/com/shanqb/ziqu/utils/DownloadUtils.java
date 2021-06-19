package com.shanqb.ziqu.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DownloadUtils {
	//下载器
    private DownloadManager downloadManager;
    private Context mContext;
    //下载的ID
    private long downloadId;
    private String name;
    private String pathstr;
    private String url;
    private boolean isNeedReport;
    //通知相关
    private String notifyTitle = "正在下载", notifyContent = "请稍等";
    //上报相关地址
    private ArrayList<String> mAdBeginDownUrl;
    private ArrayList<String> mAdEndDownUrl;
    private ArrayList<String> mAdBeginInstallUrl;
    private ArrayList<String> mAdEndInstallUrl;
 
 
    public DownloadUtils( Context context,  String url,  String name) {
        this.mContext = context;
        this.name = name;
        this.url = url;
    }
 
    public DownloadUtils setNotify( String title,  String content) {
        notifyTitle = title;
        notifyContent = content;
        return this;
    }
 
    public void startDownloadWithNoReport() {
        isNeedReport = false;
        downloadAPK(url, name);
    }
 
    public void startDownloadWithReport(ArrayList<String> appBeginDown, ArrayList<String> appEndDown, ArrayList<String> appBegininstall, ArrayList<String> appEndinstall) {
        isNeedReport = true;
        mAdBeginDownUrl = appBeginDown;
        mAdEndDownUrl = appEndDown;
        mAdBeginInstallUrl = appBegininstall;
        mAdEndInstallUrl = appEndinstall;
 
        downloadAPK(url, name);
    }
 
    //下载apk
    private void downloadAPK(String url, String name) {
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false);
        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle(notifyTitle);
        request.setDescription(notifyContent);
        request.setVisibleInDownloadsUi(true);
 
        //设置下载的路径
        File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), name);
        request.setDestinationUri(Uri.fromFile(file));
        pathstr = file.getAbsolutePath();
        //获取DownloadManager
        if (downloadManager == null)
            downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        if (downloadManager != null) {
            downloadId = downloadManager.enqueue(request);
        }
        if (isNeedReport) {  //是否需要监听/上报安装状态
            
            //上报开始下载
//            ReportUtils.reportAdBeginDownload(mAdBeginDownUrl);
            //需要上报则注册广播
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
            intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
            intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
            intentFilter.addDataScheme("package");
            mContext.registerReceiver(installStatusReceiver, intentFilter);
        }
 
        //注册广播接收者，监听下载状态
        mContext.registerReceiver(downloadStatusReceiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
 
    //广播监听下载的各个状态
    private BroadcastReceiver downloadStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };
 
    //广播监听安装的各个状态
    private BroadcastReceiver installStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            /**如果知道包名此处可以判断包名就知道具体是哪个app安装成功*/
            
            //上报安装成功：无论是什么状态，只要是安装完成就上报
//            ReportUtils.reportAdEndInstall(mAdEndInstallUrl);
            if (mContext != null && installStatusReceiver != null) {
                //记得反注册接收器
                mContext.unregisterReceiver(installStatusReceiver);
                if (mContext instanceof Activity){
                    ((Activity) mContext).finish();
                }
            }
 
        }
    };
 
    //检查下载状态
    private void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    getDownloadPercent(cursor);
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //上报下载完成
                    if (isNeedReport) {
//                        ReportUtils.reportAdEndDownload(mAdEndDownUrl);
                    }
                    //下载完成安装APK
                    installAPK();
                    cursor.close();
                    if (mContext != null && downloadStatusReceiver != null)
                        mContext.unregisterReceiver(downloadStatusReceiver);
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    LogUtils.debug("下载失败");
                    cursor.close();
                    if (mContext != null && downloadStatusReceiver != null)
                        mContext.unregisterReceiver(downloadStatusReceiver);
                    break;
            }
        }
    }
 
    /*获取下载进度*/
    private int getDownloadPercent(long downloadId){
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c =  downloadManager.query(query);
        if(c.moveToFirst()){
            int downloadBytesIdx = c.getColumnIndexOrThrow(
                    DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
            int totalBytesIdx = c.getColumnIndexOrThrow(
                    DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            long totalBytes = c.getLong(totalBytesIdx);
            long downloadBytes = c.getLong(downloadBytesIdx);
            return (int) (downloadBytes * 100 / totalBytes);
        }
        return 0;
    }
    
    /*获取下载进度*/
    private int getDownloadPercent(Cursor c){
        if(c.moveToFirst()){
            int downloadBytesIdx = c.getColumnIndexOrThrow(
                    DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
            int totalBytesIdx = c.getColumnIndexOrThrow(
                    DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            long totalBytes = c.getLong(totalBytesIdx);
            long downloadBytes = c.getLong(downloadBytesIdx);
            return (int) (downloadBytes * 100 / totalBytes);
        }
        return 0;
    }
 
    private void installAPK() {
        setPermission(pathstr);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Android 7.0以上要使用FileProvider
        if (Build.VERSION.SDK_INT >= 24) {
            File file = (new File(pathstr));
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中声明保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".hytechad.fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS, name)), "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
 
        if (isNeedReport) {
            //上报开始安装
//            ReportUtils.reportAdBeginInstall(mAdBeginInstallUrl);
        }
    }
 
    //修改文件权限
    private void setPermission(String absolutePath) {
        String command = "chmod " + "777" + " " + absolutePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
