package com.shanqb.dingyaolianmeng.activity;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

public class DownLoadAppFileUtil
{

	public static File updateDir = null;
	public static File updateFile = null;
	public static final String app = "sqb";

	public static boolean isCreateFileSucess;

	public static void createFile(String app_name) {

		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			isCreateFileSucess = true;
			updateDir = new File(Environment.getExternalStorageDirectory() + "/"
					+ app + "/");
			updateFile = new File(updateDir + "/" + app_name + ".apk");

			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					isCreateFileSucess = false;
					e.printStackTrace();
				}
			}

		}
		else {
			isCreateFileSucess = false;
		}
	}
}