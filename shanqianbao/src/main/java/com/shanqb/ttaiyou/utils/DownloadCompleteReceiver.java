package com.shanqb.ttaiyou.utils;

import java.io.File;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.FileProvider;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

public class DownloadCompleteReceiver extends BroadcastReceiver {

	private String DOWNLOADPATH = "/download/";
	
	@Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                LogUtils.debug("downloadId:{"+downloadId+"}");
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
                String type = downloadManager.getMimeTypeForDownloadedFile(downloadId);
                LogUtils.debug("getMimeTypeForDownloadedFile:{"+type+"}");
                if (TextUtils.isEmpty(type)) {
                    type = "*/*";
                }
                Uri uri = downloadManager.getUriForDownloadedFile(downloadId);
//                LogUtils.debug("UriForDownloadedFile:{"+uri+"}");
//                if (uri != null) {
//                    Intent handlerIntent = new Intent(Intent.ACTION_VIEW);
//                    handlerIntent.setDataAndType(uri, type);
//                    context.startActivity(handlerIntent);
//                }
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);

                Cursor cursor = downloadManager.query(query);
                if (cursor.moveToFirst()){
                    // 获取文件下载路径
                    String fileName = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));

                    // 如果文件名不为空，说明文件已存在,则进行自动安装apk
                    if (fileName != null){
                    	installApk1(context,fileName);
                    }
                }
                cursor.close();
            }
        }
    }
	
	/**
     * 安装APK
     */
    private void installApk1(Context context,String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOWNLOADS,filePath);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
//            File file = new File(context.getExternalFilesDir(null)+"/"+ Environment.DIRECTORY_DOWNLOADS,context.getString(R.string.app_name_en)+".apk");
            Uri apkUri= FileProvider.getUriForFile(context, "com.shanqb.wallet.fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else{
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

	
	private void installAPK(Uri apk, Context context,String fileName) {
        if (Build.VERSION.SDK_INT < 23) {
            Intent intents = new Intent();
            intents.setAction(Intent.ACTION_VIEW);
//            intents.addCategory("android.intent.category.DEFAULT");
            intents.setDataAndType(apk, "application/vnd.android.package-archive");
            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intents);
        } else if (Build.VERSION.SDK_INT >= 24) {
            install(context,fileName);
        } else {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + DOWNLOADPATH + "app-release.apk");
            if (file.exists()) {
                openFile(file, context);
            } else {
            }
        }
    }
	/**
     * android7.0之后的更新
     * 通过隐式意图调用系统安装程序安装APK
     */
    public void install(Context context,String fileName) {
        File file = new File(fileName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
        Uri apkUri =
                FileProvider.getUriForFile(context, "com.shanqb.wallet.fileprovider", file);
        intent.addCategory("android.intent.category.DEFAULT");
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    private void installAPK(Context context) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + DOWNLOADPATH + "huijuquanqiu.apk");
        if (file.exists()) {
            openFile(file, context);
        } else {
            Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
        }
    }

	/**
	 * android6.0之后的升级更新
	 *
	 * @param file
	 * @param context
	 */
	public void openFile(File file, Context context) {
	    Intent intent = new Intent();
	    intent.addFlags(268435456);
	    intent.setAction("android.intent.action.VIEW");
	    String type = getMIMEType(file);
	    intent.setDataAndType(Uri.fromFile(file), type);
	    try {
	        context.startActivity(intent);
	    } catch (Exception var5) {
	        var5.printStackTrace();
	        Toast.makeText(context, "没有找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
	    }
	}
	
	public String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }

}
