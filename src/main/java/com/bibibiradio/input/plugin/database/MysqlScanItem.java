package com.bibibiradio.input.plugin.database;

import java.util.Date;

public class MysqlScanItem {
	private long itemId;
	private String method;
	private String url;
	private int requestContentType;
	private int responseStatusCode;
	private String responseContentType;
	private byte[] requestOrig;
	private byte[] responseOrig;
	private Date insertScanTime;
	private Date scanTime;
	private byte[] itemHash;
	private int isScan;
	
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public int getIsScan() {
		return isScan;
	}
	public void setIsScan(int isScan) {
		this.isScan = isScan;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public byte[] getItemHash() {
		return itemHash;
	}
	public void setItemHash(byte[] itemHash) {
		this.itemHash = itemHash;
	}
	public Date getInsertScanTime() {
		return insertScanTime;
	}
	public void setInsertScanTime(Date insertScanTime) {
		this.insertScanTime = insertScanTime;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public int getRequestContentType() {
		return requestContentType;
	}
	public void setRequestContentType(int requestContentType) {
		this.requestContentType = requestContentType;
	}
	public int getResponseStatusCode() {
		return responseStatusCode;
	}
	public void setResponseStatusCode(int responseStatusCode) {
		this.responseStatusCode = responseStatusCode;
	}
	public String getResponseContentType() {
		return responseContentType;
	}
	public void setResponseContentType(String responseContentType) {
		this.responseContentType = responseContentType;
	}
	public byte[] getRequestOrig() {
		return requestOrig;
	}
	public void setRequestOrig(byte[] requestOrig) {
		this.requestOrig = requestOrig;
	}
	public byte[] getResponseOrig() {
		return responseOrig;
	}
	public void setResponseOrig(byte[] responseOrig) {
		this.responseOrig = responseOrig;
	}
}
