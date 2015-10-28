package com.bibibiradio.burp.httpproxy;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

import com.bibibiradio.commoncache.CacheWithTimeLimit;

import burp.IBurpExtenderCallbacks;
import burp.IHttpRequestResponse;
import burp.IInterceptedProxyMessage;

public class HttpPackageRecordor {
	static private CacheWithTimeLimit requestResponseCache = null;
	//static private HashMap<Integer,RequestResponsePair> requestResponseMap = null;
	private OutputStream output = null;
	private IBurpExtenderCallbacks callback =null;
	
	public HttpPackageRecordor(IBurpExtenderCallbacks callback){
		this.callback = callback;
		this.output = callback.getStdout();
		callback.getHelpers();
		//requestResponseMap = new HashMap<Integer,RequestResponsePair>();
		requestResponseCache = new CacheWithTimeLimit();
		requestResponseCache.setTimeLimit(500000);
	}
	
	public void record(boolean messageIsRequest, IInterceptedProxyMessage message){
		int ref = message.getMessageReference();
		Integer refInteger = new Integer(ref);
		IHttpRequestResponse httpRP = message.getMessageInfo();
		RequestResponsePair rPPair = null;
		
		rPPair = (RequestResponsePair) requestResponseCache.getData(refInteger);
		//not have pair
		if(rPPair == null && messageIsRequest == false){
			return;
		}
		
		if(rPPair == null && messageIsRequest == true){
			rPPair = new RequestResponsePair();
			rPPair.setCallback(callback);
			rPPair.setRef(ref);
			rPPair.setRequest(httpRP.getRequest());
			rPPair.setHost(httpRP.getHttpService().getHost());
			rPPair.setPort(httpRP.getHttpService().getPort());
			rPPair.setProtocol(httpRP.getHttpService().getProtocol());
			if(rPPair.isComplete()){
				rPPair.dump();
			}else{
				requestResponseCache.inputData(refInteger, rPPair);
			}
			return;
		}
		
		//have pair
		if(rPPair != null && messageIsRequest == true){
			requestResponseCache.removeData(refInteger);
			return;
		}
		
		if(rPPair != null && messageIsRequest == false){
			if(httpRP.getResponse().length >= 2000000){
				rPPair.setResponse(null);
			}else{
				rPPair.setResponse(httpRP.getResponse());
			}
			if(rPPair.isComplete()){
				rPPair.dump();
			}
			requestResponseCache.removeData(refInteger);
			return;
		}
	}
}
