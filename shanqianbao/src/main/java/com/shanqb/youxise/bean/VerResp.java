package com.shanqb.youxise.bean;

public class VerResp
{
	private String status;
	
	private VersionData version;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public VersionData getVersion() {
		return version;
	}

	public void setVersion(VersionData version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "VerResp [status=" + status + ", version=" + version + "]";
	}
	
	
}
