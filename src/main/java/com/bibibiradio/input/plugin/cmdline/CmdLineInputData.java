package com.bibibiradio.input.plugin.cmdline;

import java.util.Map;

import com.bibibiradio.input.plugin.IInputData;

public class CmdLineInputData implements IInputData {
	private String url;
	private byte[] reqBody;
	private Map<String,String> reqHeader;
	private Map<String,String> resHeader;
	private String method;
	private byte[] resBody;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public byte[] getReqBody() {
		return reqBody;
	}
	public void setReqBody(byte[] reqBody) {
		this.reqBody = reqBody;
	}
	public Map<String, String> getReqHeader() {
		return reqHeader;
	}
	public void setReqHeader(Map<String, String> reqHeader) {
		this.reqHeader = reqHeader;
	}
	public Map<String, String> getResHeader() {
		return resHeader;
	}
	public void setResHeader(Map<String, String> resHeader) {
		this.resHeader = resHeader;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public byte[] getResBody() {
		return resBody;
	}
	public void setResBody(byte[] resBody) {
		this.resBody = resBody;
	}
}
