package com.shanqb.myTool.taojin91;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.ValueCallback;
import android.widget.Toast;

public class WebChoosePicUtil {
    public static final int ChoicePicCode =10001;
    private Activity aty;
    private ValueCallback<Uri> uploadFile;
    private ValueCallback<Uri[]> uploadFiles;
    public WebChoosePicUtil(Activity aty, ValueCallback<Uri> callback,ValueCallback<Uri[]> callbacks){
        this.aty =aty;
        this.uploadFile = callback;
        this.uploadFiles = callbacks;
    }
    public void choicePic() {
        //图库选择器
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            aty.startActivityForResult(Intent.createChooser(intent, "选择图片"), ChoicePicCode);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(aty.getApplicationContext(),"很抱歉，无法打开相册",Toast.LENGTH_SHORT).show();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ChoicePicCode){
            if (resultCode == Activity.RESULT_OK) {
                if (uploadFile != null) {
                    if (data!=null){
                        uploadFile.onReceiveValue(data.getData());
                    }else{
                        uploadFile.onReceiveValue(null);
                    }
                    uploadFile = null;
                }
                if (uploadFiles != null) {
                    if (data!=null){
                        uploadFiles.onReceiveValue(new Uri[]{data.getData()});
                    }else{
                        uploadFiles.onReceiveValue(null);
                    }
                    uploadFiles = null;
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (uploadFile != null) {
                    uploadFile.onReceiveValue(null);
                    uploadFile = null;
                }
                if (uploadFiles != null) {
                    uploadFiles.onReceiveValue(null);
                    uploadFiles = null;
                }
            }
        }
    }
}
