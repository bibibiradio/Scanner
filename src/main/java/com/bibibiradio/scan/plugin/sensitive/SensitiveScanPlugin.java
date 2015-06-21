package com.bibibiradio.scan.plugin.sensitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bibibiradio.httpsender.HttpSender;
import com.bibibiradio.httpsender.HttpSenderImplV1;
import com.bibibiradio.httpsender.ResponseData;
import com.bibibiradio.input.plugin.IInputData;
import com.bibibiradio.scan.plugin.IScanPlugin;
import com.bibibiradio.scan.plugin.IVulnItem;
import com.bibibiradio.scan.plugin.SimpleVulnItem;

public class SensitiveScanPlugin implements IScanPlugin {
	static private HttpSender httpSender = null;
	static private String configPath = null;
	static private Pattern mobileNumPattern=Pattern.compile("[^0-9](?:13[0-9]|14[57]|15[0-35-9]|170|18[0-9])\\d{8}[^0-9]",Pattern.CASE_INSENSITIVE);
	//static public Pattern bankCardPattern=Pattern.compile("[^0-9a-zA-Z][34569][0-9]{12,18}[^0-9a-zA-Z]",Pattern.CASE_INSENSITIVE);
	static private Pattern idCardPattern=Pattern.compile("[^0-9][1-9]\\d{5}[12][09]\\d{2}((0\\d)|(1[0-2]))(([0-2]\\d)|(3[0-1]))(\\d{3}[xX0-9])[^0-9]",Pattern.CASE_INSENSITIVE);
	static private Pattern emailPattern=Pattern.compile("(([a-zA-Z0-9_\\.\\-])+@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4}))",Pattern.CASE_INSENSITIVE);
	@Override
	public boolean open(String configPath) {
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
		byte[] resBody = null;
		resBody = getResponseBody(inputData);
		if(resBody == null){
			return null;
		}
		
		String resBodyStr = new String(resBody);
		
		List<IVulnItem> vulnItems = new ArrayList<IVulnItem>();
		
		findPatternByRegx(vulnItems,idCardPattern,resBodyStr,"idCard",inputData);
		findPatternByRegx(vulnItems,mobileNumPattern,resBodyStr,"mobileNum",inputData);
		findPatternByRegx(vulnItems,emailPattern,resBodyStr,"emailNum",inputData);
		
		if(vulnItems.size()<=0){
			return null;
		}

		return vulnItems.toArray(new SimpleVulnItem[0]);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}
	
	private void findPatternByRegx(List<IVulnItem> vulnItems,Pattern pattern,String content,String name,IInputData inputData){
		Matcher match = pattern.matcher(content);
		while(match.find()){
			SimpleVulnItem vulnItem = new SimpleVulnItem();
			vulnItem.setType("sensitive "+name);
			vulnItem.setUrl(inputData.getUrl());
			vulnItem.setMethod(inputData.getMethod());
			vulnItem.setPayload(name);
			vulnItem.setDetail(match.group(0));
			vulnItem.setHashcode("123".getBytes());
			vulnItem.setPos(String.valueOf(match.start(0)));
			vulnItems.add(vulnItem);
		}
	}
	
	private byte[] getResponseBody(IInputData inputData){
		byte[] retResBody = null;
		
		String url = null;
		String method = null;
		int intMethod = -1;
		Map<String,String> reqHeader = null;
		byte[] reqBody = null;
		
		ResponseData resData = null;
		
		retResBody = inputData.getResBody();
		if(retResBody != null){
			return retResBody;
		}
		
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
		
		resData = httpSender.send(url, intMethod, reqHeader, reqBody);
		if(resData.getResponseContent() != null){
			return resData.getResponseContent();
		}
		
		return null;
	}

}
