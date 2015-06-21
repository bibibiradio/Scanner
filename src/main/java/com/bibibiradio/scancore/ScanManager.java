package com.bibibiradio.scancore;

import com.bibibiradio.input.plugin.xlburpproxy.XlBurpProxyInputData;
import com.bibibiradio.scan.plugin.IVulnItem;
import com.bibibiradio.scan.plugin.sensitive.SensitiveScanPlugin;

public class ScanManager {
	static private SensitiveScanPlugin sensitiveScanPlugin = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		run(args);
	}
	
	public static void run(String[] args){
		XlBurpProxyInputData inputData = new XlBurpProxyInputData();
		IVulnItem[] vulnItems = null;
		inputData.setUrl(args[1]);
		inputData.setMethod(args[0]);
		
		if(sensitiveScanPlugin == null){
			sensitiveScanPlugin = new SensitiveScanPlugin();
			sensitiveScanPlugin.open("abc");
		}
		
		vulnItems = sensitiveScanPlugin.scan(inputData);
		
		if(vulnItems != null){
			for(int i=0;i<vulnItems.length;i++){
				System.out.println(vulnItems[i].getType()+" "+vulnItems[i].getPos()+" "+vulnItems[i].getDetail());
			}
		}else{
			System.out.println("no vuln");
		}
	}

}
