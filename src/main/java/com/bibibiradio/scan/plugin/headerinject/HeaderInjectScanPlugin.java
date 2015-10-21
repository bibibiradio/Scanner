package com.bibibiradio.scan.plugin.headerinject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.bibibiradio.httpsender.HttpSender;
import com.bibibiradio.httpsender.HttpSenderImplV1;
import com.bibibiradio.httpsender.ResponseData;
import com.bibibiradio.input.plugin.IInputData;
import com.bibibiradio.scan.common.HttpParams;
import com.bibibiradio.scan.plugin.IScanPlugin;
import com.bibibiradio.scan.plugin.IVulnItem;

public class HeaderInjectScanPlugin implements IScanPlugin {
	static private HttpSender httpSender;
	static private Logger logger = Logger.getLogger(HeaderInjectScanPlugin.class);
	static private String[] payload;
	private String configPath;
	
	static{
		payload = new String[1];
		payload[0] = "aaa\r\nTEST: abcdefg";
	}
	
	@Override
	public boolean open(String config) {
		// TODO Auto-generated method stub
		this.configPath = configPath;
		if(httpSender == null){
			httpSender = new HttpSenderImplV1();
			httpSender.setRetryTime(3);
			httpSender.setSendFreq(1500);
			httpSender.setTimeout(10000);
			httpSender.setSoTimeout(20000);
			httpSender.start();
		}
		return true;
	}

	@Override
	public IVulnItem[] scan(IInputData inputData) {
		// TODO Auto-generated method stub
		List<IVulnItem> vulnItems = new ArrayList<IVulnItem>();
		int intMethod = -1;
		
		if("GET".equalsIgnoreCase(inputData.getMethod())){
			intMethod = 0;
		}else if("POST".equalsIgnoreCase(inputData.getMethod())){
			intMethod = 1;
		}else if("PUT".equalsIgnoreCase(inputData.getMethod())){
			intMethod = 2;
		}
		
		try {
			URL uurl = new URL(inputData.getUrl());
			String query = uurl.getQuery();
			HttpParams urlHttpparams = new HttpParams();
			HttpParams postHttpParams = new HttpParams();
			urlHttpparams.setParams(query);
			urlHttpparams.syn();
			Map<String,String> urlParams = urlHttpparams.getKeyValues();
			Map<String,String> postParams = null;
			if(inputData.getReqBody() != null){
				String postData = new String(inputData.getReqBody(),"UTF-8");
				postHttpParams.setParams(postData);
				postHttpParams.syn();
				postParams = postHttpParams.getKeyValues();
			}
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			logger.error("error message",ex);
		}
		return null;
	}
	
//	private boolean check(URL uurl,Map<String,String> urlParams,Map<String,String> postParams,Map<String,String> header,String method) throws Exception{
//		int intMethod = -1;
//		
//		HttpParams urlHttpparams = new HttpParams();
//		HttpParams postHttpParams = new HttpParams();
//		
//		if("GET".equalsIgnoreCase(method)){
//			intMethod = 0;
//		}else if("POST".equalsIgnoreCase(method)){
//			intMethod = 1;
//		}else if("PUT".equalsIgnoreCase(method)){
//			intMethod = 2;
//		}
//		
//		if(postParams != null){
//			Map<String,String> clonePostParams = new HashMap<String,String>();
//			clonePostParams.putAll(postParams);
//			Iterator<Entry<String, String>> iter = postParams.entrySet().iterator();
//			while(iter.hasNext()){
//				Entry<String, String> entry = iter.next();
//				String oldValue = clonePostParams.put(entry.getKey(), payload[0]);
//				postHttpParams.setKeyValues(clonePostParams);
//				postHttpParams.syn();
//				String postData = postHttpParams.getParams();
//				ResponseData res = httpSender.send(uurl.toString(), intMethod, header, postData.getBytes());
//				if(res == null){
//					continue;
//				}
//				
//				
//			}
//		}
//	}
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
