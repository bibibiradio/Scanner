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
import com.bibibiradio.scan.common.HttpReqImpl;
import com.bibibiradio.scan.plugin.IScanPlugin;
import com.bibibiradio.scan.plugin.IVulnItem;
import com.bibibiradio.scan.plugin.SimpleVulnItem;

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
			httpSender.setHttpProxy("127.0.0.1", 8080);
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
		
		HttpReqImpl httpReqImpl = new HttpReqImpl();
        
		
		try {
		    httpReqImpl.setUrlSyn(inputData.getUrl());
	        httpReqImpl.setReqHeaderSyn(inputData.getReqHeader());
	        if(inputData.getReqBody() != null){
	        	httpReqImpl.setPostBodySyn(new String(inputData.getReqBody(),"UTF-8"));
	        }
	        
	        Iterator<Entry<String, String>> queryParams = httpReqImpl.getQueryIter();
	        if(queryParams != null){
	            while(queryParams.hasNext()){
	                Entry<String,String> queryItem = queryParams.next();
	                
	                String key = queryItem.getKey();
	                String value = queryItem.getValue();
	                
	                HttpReqImpl cloneHttpReqImpl = httpReqImpl.deepClone();
	                
	                for(String aPayload : payload){
	                    cloneHttpReqImpl.setQueryItemSyn(key, aPayload);
	                    
	                    byte[] postBody = null;
	                    if(cloneHttpReqImpl.getPostBody() != null){
	                    	postBody = cloneHttpReqImpl.getPostBody().getBytes();
	                    }
	                    ResponseData resData = httpSender.send(cloneHttpReqImpl.getUrl(), intMethod, cloneHttpReqImpl.getReqHeader(), postBody);
	                    if(resData == null){
	                        continue;
	                    }
	                    
	                    if(check(resData)){
	                        SimpleVulnItem vulnItem = new SimpleVulnItem();
	                        vulnItem.setType("headerInject");
	                        vulnItem.setUrl(inputData.getUrl());
	                        vulnItem.setMethod(inputData.getMethod());
	                        vulnItem.setPayload(aPayload);
	                        vulnItem.setDetail("urlQuery:"+key);
	                        vulnItem.setHashcode("123".getBytes());
	                        vulnItem.setPos(key);
	                        vulnItem.setInputData(inputData);
	                        vulnItems.add(vulnItem);
	                    }
	                }
	            }
	        }
	        
	        Iterator<Entry<String, String>> postParams = httpReqImpl.getPostIter();
            if(postParams != null){
                while(postParams.hasNext()){
                    Entry<String,String> postItem = postParams.next();
                    
                    String key = postItem.getKey();
                    String value = postItem.getValue();
                    
                    HttpReqImpl cloneHttpReqImpl = httpReqImpl.deepClone();
                    
                    for(String aPayload : payload){
                        cloneHttpReqImpl.setPostItemSyn(key, value);
                        ResponseData resData = httpSender.send(cloneHttpReqImpl.getUrl(), intMethod, cloneHttpReqImpl.getReqHeader(), cloneHttpReqImpl.getPostBody().getBytes());
                        if(resData == null){
                            continue;
                        }
                        
                        if(check(resData)){
                            SimpleVulnItem vulnItem = new SimpleVulnItem();
                            vulnItem.setType("headerInject");
                            vulnItem.setUrl(inputData.getUrl());
                            vulnItem.setMethod(inputData.getMethod());
                            vulnItem.setPayload(aPayload);
                            vulnItem.setDetail("postQuery:"+key);
                            vulnItem.setHashcode("123".getBytes());
                            vulnItem.setPos(key);
                            vulnItem.setInputData(inputData);
                            vulnItems.add(vulnItem);
                        }
                    }
                }
            }
            
            if(vulnItems.size()<=0){
                return null;
            }

            return vulnItems.toArray(new SimpleVulnItem[0]);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			logger.error("error message",ex);
		}
		return null;
	}
	
	private boolean check(ResponseData resData){
	    String token = resData.getResponseHeader().get("TEST");
        if(token == null){
            return false;
        }
        
        if(token.equals("abcdefg")){
            return true;
        }
        
        return false;
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
