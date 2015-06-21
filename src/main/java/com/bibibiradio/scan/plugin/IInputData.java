package com.bibibiradio.scan.plugin;

import java.util.Map;

public interface IInputData {
	public String getUrl();
	public byte[] getReqBody();
	public Map<String,String> getReqHeader();
	public Map<String,String> getResHeader();
	public byte[] getResBody();
}
