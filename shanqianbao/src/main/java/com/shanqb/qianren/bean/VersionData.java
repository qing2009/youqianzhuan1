package com.shanqb.qianren.bean;

public class VersionData
{
	private LatestData latest;
	
	private MinimalData minimal;

	public LatestData getLatest() {
		return latest;
	}

	public MinimalData getMinimal() {
		return minimal;
	}

	public void setLatest(LatestData latest) {
		this.latest = latest;
	}

	public void setMinimal(MinimalData minimal) {
		this.minimal = minimal;
	}

	@Override
	public String toString() {
		return "VersionData [latest=" + latest + ", minimal=" + minimal + "]";
	}
}
