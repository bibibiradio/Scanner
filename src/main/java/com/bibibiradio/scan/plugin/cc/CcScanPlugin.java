package com.bibibiradio.scan.plugin.cc;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bibibiradio.commoncache.CacheWithTimeLimit;
import com.bibibiradio.httpsender.HttpSender;
import com.bibibiradio.httpsender.HttpSenderImplV1;
import com.bibibiradio.httpsender.ResponseData;
import com.bibibiradio.input.plugin.IInputData;
import com.bibibiradio.scan.plugin.IScanPlugin;
import com.bibibiradio.scan.plugin.IVulnItem;
import com.bibibiradio.scan.plugin.SimpleVulnItem;

public class CcScanPlugin implements IScanPlugin {
	static private HttpSender httpSender = null;
	static CacheWithTimeLimit hostCache = null;
	static private long brand = 2211000;
	static private long vulnTimeLimit = 1600;
	@Override
	public boolean open(String config) {
		// TODO Auto-generated method stub
		if(httpSender == null){
			httpSender = new HttpSenderImplV1();
			httpSender.setRetryTime(3);
			httpSender.setSendFreq(1500);
			httpSender.setTimeout(10000);
			httpSender.setSoTimeout(20000);
			httpSender.start();
		}
		
		if(hostCache == null){
			hostCache = new CacheWithTimeLimit();
			hostCache.setTimeLimit(100000);
		}
		
		return true;
	}

	@Override
	public IVulnItem[] scan(IInputData inputData) {
		// TODO Auto-generated method stub
		double connectTime = 0;
		double realTime = 0;
		double networkGetDataTime = 0;
		double serverExecuteTime = 0;
		
		long[] realTime2 = null;
		String detail;
		
		List<IVulnItem> vulnItems = new ArrayList<IVulnItem>();
		
		connectTime = getConnectTime(inputData.getUrl());
		if(connectTime<0){
			return null;
		}
		//System.out.println(connectTime);
		
		realTime2 = getRealTime(inputData);
		if(realTime2 == null){
			return null;
		}
		
		realTime = realTime2[0];
		networkGetDataTime = realTime2[1]/((double)brand)+(realTime2[1]/1500+1)*connectTime*2;
		
		serverExecuteTime = realTime - 6*connectTime - networkGetDataTime;
		
		detail = "et:"+serverExecuteTime+" ct:"+connectTime*6+" rt:"+realTime+" nt:"+networkGetDataTime;
		//System.out.println(detail);
		//System.out.println("et:"+serverExecuteTime+" ct:"+connectTime*6+" rt:"+realTime+" nt:"+networkGetDataTime+" url:"+inputData.getUrl());
		
		if(serverExecuteTime >= vulnTimeLimit){
			SimpleVulnItem vulnItem = new SimpleVulnItem();
			vulnItem.setInputData(inputData);
			vulnItem.setHashcode("123".getBytes());
			vulnItem.setMethod(inputData.getMethod());
			vulnItem.setPayload("execute");
			vulnItem.setType("CC");
			vulnItem.setPos("0");
			vulnItem.setUrl(inputData.getUrl());
			vulnItem.setDetail(detail);
			vulnItems.add(vulnItem);
		}
		
		if(vulnItems.size()<=0){
			return null;
		}
		
		return vulnItems.toArray(new SimpleVulnItem[0]);
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}
	
	private long getConnectTime(String url){
		Process pingProcess = null;
		InputStream pingInput = null;
		byte[] pingInputBytes = null;
		String pingInputString = null;
		URL uurl = null;
		long baseMs = -1;
		Long cacheBaseMs = null;
		
		hostCache.removeCheckPointExecute();
		
		try {
			uurl = new URL(url);
			cacheBaseMs = (Long) hostCache.getData(uurl.getHost());
			if(cacheBaseMs != null){
				return cacheBaseMs.longValue();
			}
			
			String cmd = "ping "+uurl.getHost();
			//System.out.println(cmd);
			pingProcess = Runtime.getRuntime().exec(cmd);
			pingInput = pingProcess.getInputStream();
			pingInputBytes = new byte[2048];
			pingProcess.waitFor();
			pingInput.read(pingInputBytes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		pingInputString = new String(pingInputBytes);
		cacheBaseMs = Long.valueOf(pingInputString.substring(pingInputString.lastIndexOf("=")+1, pingInputString.lastIndexOf("ms")).trim());
		hostCache.inputData(uurl.getHost(), cacheBaseMs);
		baseMs = cacheBaseMs.longValue();
		//System.out.println(baseMs);
		return baseMs;
	}
	
	private long[] getRealTime(IInputData inputData){
		String url = null;
		String method = null;
		int intMethod = -1;
		Map<String,String> reqHeader = null;
		byte[] reqBody = null;
		ResponseData resData = null;
		
		long start = -1,end = -1;
		long[] returnLong = new long[2];
		
		url = inputData.getUrl();
		method = inputData.getMethod();
		reqHeader = inputData.getReqHeader();
		reqBody = inputData.getReqBody();
		
		if(url == null || method == null){
			return null;
		}
		
		if("GET".equalsIgnoreCase(method)){
			intMethod = 0;
		}else if("POST".equalsIgnoreCase(method)){
			intMethod = 1;
		}else if("PUT".equalsIgnoreCase(method)){
			intMethod = 2;
		}
		
		if(intMethod == -1){
			return null;
		}
		
		start = System.currentTimeMillis();
		resData = httpSender.send(url, intMethod, reqHeader, reqBody);
		if(resData.getResponseContent() == null){
			return null;
		}
		end = System.currentTimeMillis();
		
		returnLong[0] = end - start;
		returnLong[1] = resData.getResponseContent().length;
		
		return returnLong;
	}

}
