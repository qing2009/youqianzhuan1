package com.shanqb.ziqu.bean;

public class LatestData
{
	private int versionCode;
	private String versionName;
	private String downloadURL;
	private String versionDesc;

	public int getVersionCode() {
		return versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public String getDownloadURL() {
		return downloadURL;
	}

	public String getVersionDesc() {
		return versionDesc;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}

	@Override
	public String toString() {
		return "LatestData [versionCode=" + versionCode + ", versionName=" + versionName
				+ ", downloadURL=" + downloadURL + ", versionDesc=" + versionDesc + "]";
	}

}
