package com.bibibiradio.scan.plugin;

import com.bibibiradio.input.plugin.IInputData;

public interface IVulnItem {
	public String getUrl();
	public String getMethod();
	public String getPayload();
	public String getDetail();
	public String getPos();
	public byte[] getHashcode();
	public String getType();
	public IInputData getInputData();
}
