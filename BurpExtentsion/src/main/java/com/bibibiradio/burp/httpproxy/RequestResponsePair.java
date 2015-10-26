package com.bibibiradio.burp.httpproxy;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import burp.IBurpExtenderCallbacks;
import burp.IExtensionHelpers;
import burp.IParameter;
import burp.IRequestInfo;
import burp.IResponseInfo;

import com.alipay.security.dbaudit.consistenthash.HashGenerator;
import com.alipay.security.dbaudit.consistenthash.Sha256HashGenerator;
import com.bibibiradio.burp.httpproxy.dbinsert.ScanItem;
import com.bibibiradio.burp.httpproxy.dbinsert.ScanItemInsertor;
import com.bibibiradio.commoncache.CacheWithTimeLimit;

public class RequestResponsePair {
	private int ref = -1;
	private byte[] request = null;
	private byte[] response = null;
	private String host = null;
	private int port = -1;
	private String protocol = null;
	
	
	static private ScanItemInsertor scanItemInsertor = null;
	static private HashGenerator hashGenerator = null;
	static private CacheWithTimeLimit scanItemCache = null;
	
	private OutputStream output = null;
	private IBurpExtenderCallbacks callback = null;
	
	static{
		scanItemInsertor= new ScanItemInsertor("/Users/xiaoleixl/SqlMapConfig.xml");
		hashGenerator = Sha256HashGenerator.getHashGenerator();
		scanItemCache = new CacheWithTimeLimit();
		scanItemCache.setTimeLimit(1000000);
	}
	
	public RequestResponsePair(){
	}
	
	public boolean isComplete(){
		if(request != null && response != null){
			return true;
		}else{
			return false;
		}
	}
	
	public void dump(){
		dumpToDb();
	}
	
	private String analyzeUrl(IRequestInfo ri){
		if(ri == null){
			return null;
		}
		String url = "";
		ByteBuffer bb = ByteBuffer.wrap(request);
		byte[] header = new byte[ri.getBodyOffset()];
		bb.get(header);
		String reqH = new String(header);
		
		String[] sp = reqH.split(" ");
		if(sp.length<2){
			return null;
		}
		
		
		return protocol+"//"+host+sp[1];
	}
	
	private String urlGetNotQuery(String url){
		return url.split("\\?")[0];
	}
	
	private void dumpToDb(){
		IExtensionHelpers helper = callback.getHelpers();
		IRequestInfo requestInfo = helper.analyzeRequest(request);
		IResponseInfo responseInfo = helper.analyzeResponse(response);
		String url = analyzeUrl(requestInfo);
		if(helper == null || requestInfo == null || responseInfo == null || url ==null || scanItemInsertor == null || scanItemCache == null){
			return;
		}
		byte[] itemHash = null;
		String needHash = requestInfo.getMethod()+" " + urlGetNotQuery(url)+" ";
		Iterator<IParameter> itor = requestInfo.getParameters().iterator();
		
		while(itor.hasNext()){
			needHash+=itor.next().getName()+" ";
		}
		
		itemHash = hashGenerator.getHash(needHash.getBytes());
		Integer isExist = (Integer) scanItemCache.getData(itemHash);
		if(isExist != null){
			return;
		}
		
		ScanItem item = null;
		item = new ScanItem();
		item.setItemHash(itemHash);
		item = scanItemInsertor.selectScanItemByHash(item);
		if(item != null){
			return;
		}
		item = new ScanItem();
		item.setInsertScanTime(new Date());
		item.setItemHash(itemHash);
		item.setMethod(requestInfo.getMethod());
		item.setRequestContentType(requestInfo.getContentType());
		item.setRequestOrig(request);
		item.setResponseStatusCode(responseInfo.getStatusCode());
		item.setResponseContentType(responseInfo.getStatedMimeType());
		item.setResponseOrig(response);
		item.setUrl(url);
		
		scanItemInsertor.insert(item);
		scanItemCache.inputData(itemHash, new Integer(1));
	}

	
	public IBurpExtenderCallbacks getCallback() {
		return callback;
	}

	public void setCallback(IBurpExtenderCallbacks callback) {
		this.callback = callback;
	}

	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public OutputStream getOutput() {
		return output;
	}

	public void setOutput(OutputStream output) {
		this.output = output;
	}

	public byte[] getRequest() {
		return request;
	}

	public void setRequest(byte[] request) {
		this.request = request;
	}

	public byte[] getResponse() {
		return response;
	}

	public void setResponse(byte[] response) {
		this.response = response;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	
}
