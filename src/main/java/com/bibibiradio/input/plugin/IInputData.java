package com.bibibiradio.input.plugin;

import java.util.Map;

public interface IInputData {
	public String getUrl();
	public String getMethod();
	public byte[] getReqBody();
	public Map<String,String> getReqHeader();
	public Map<String,String> getResHeader();
	public byte[] getResBody();
}
