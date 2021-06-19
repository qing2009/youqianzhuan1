package com.shanqb.ziqu.bean;

public class MinimalData
{
	private int versionCode;
	private String versionName;
	private String message;

	public int getVersionCode() {
		return versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public String getMessage() {
		return message;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "MinimalData [versionCode=" + versionCode + ", versionName=" + versionName
				+ ", message=" + message + "]";
	}

}
