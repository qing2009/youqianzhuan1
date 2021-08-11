/*
 * Copyright (c) 2017 LingoChamp Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shanqb.tongda.taojin91;

import android.content.Context;
import android.os.Environment;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;

public class DemoUtil {

    public static final String URL =
            "https://cdn.llscdn.com/yy/files/tkzpx40x-lls-LLS-5.7-785-20171108-111118.apk";

    public static void calcProgressToView(ProgressBar progressBar, long offset, long total) {
        final float percent = (float) offset / total;
        progressBar.setProgress((int) (percent * progressBar.getMax()));
    }


    public static File getParentFile(@NonNull Context context) {
        final File externalSaveDir = context.getExternalCacheDir();
        if (externalSaveDir == null) {
            return context.getCacheDir();
        } else {
            return externalSaveDir;
        }
    }

    public static ArrayList<String> getFileName(File file, String type) {
        ArrayList<String>  result = new ArrayList<String>();
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (!files[i].isDirectory()) {
                String fileName = files[i].getName();
                if (fileName.trim().toLowerCase().endsWith(type)) {
                    result.add(fileName);
                }
            }
        }
        return result;
    }

    public static boolean switch_configurefile(File file,String fileName){
        //这个方法是获取内部存储的根路径
        //getFilesDir().getAbsolutePath() =/data/user/0/packname/files
        boolean pdtemp = false;

        ArrayList<String> ss = getFileName(file, ".apk");
        for (String s : ss) {
            if (s.equals(fileName))pdtemp=true;
        }
        return pdtemp;
    }

    public static void crSDFile(String... folder) {

        int length = folder.length;
        String genFolder = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/";
        String str = genFolder;
        File file;

        for (int i = 0; i < length; i++) {

            str = str + folder[i] + "/";
            file = new File(str);

            if (!file.exists()) {
                file.mkdir();

            }

        }

    }
}
