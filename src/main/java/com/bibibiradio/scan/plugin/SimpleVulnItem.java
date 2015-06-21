package com.bibibiradio.scan.plugin;

public class SimpleVulnItem implements IVulnItem {
	private String url;
	private String method;
	private String payload;
	private String detail;
	private String pos;
	private byte[] hashcode;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public byte[] getHashcode() {
		return hashcode;
	}
	public void setHashcode(byte[] hashcode) {
		this.hashcode = hashcode;
	}

	

}
