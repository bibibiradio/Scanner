package com.bibibiradio.scan.plugin;

public interface IVulnItem {
	public String getUrl();
	public String getMethod();
	public String getPayload();
	public String getDetail();
	public String getPos();
	public byte[] getHashcode();
	public String getType();
}
