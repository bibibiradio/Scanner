package com.bibibiradio.scan.plugin.cc;

import java.io.InputStream;

import com.bibibiradio.httpsender.HttpSender;
import com.bibibiradio.httpsender.HttpSenderImplV1;
import com.bibibiradio.input.plugin.IInputData;
import com.bibibiradio.scan.plugin.IScanPlugin;
import com.bibibiradio.scan.plugin.IVulnItem;

public class CcScanPlugin implements IScanPlugin {
	static private HttpSender httpSender = null;
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
		
		return true;
	}

	@Override
	public IVulnItem[] scan(IInputData inputData) {
		// TODO Auto-generated method stub
		Process pingProcess = null;
		InputStream pingInput = null;
		byte[] pingInputBytes = null;
		String pingInputString = null;
		long baseMs = -1;
		try {
			pingProcess = Runtime.getRuntime().exec("ping 127.0.0.1");
			pingInput = pingProcess.getInputStream();
			pingInputBytes = new byte[2048];
			pingProcess.waitFor();
			pingInput.read(pingInputBytes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		pingInputString = new String(pingInputBytes);
		baseMs = Integer.valueOf(pingInputString.substring(pingInputString.lastIndexOf("=")+1, pingInputString.lastIndexOf("ms")).trim()).intValue();
		System.out.println(baseMs);
		//System.out.println(pingInputString);
		
		
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
