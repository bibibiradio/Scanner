package com.bibibiradio.scancore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bibibiradio.input.plugin.IInputData;
import com.bibibiradio.input.plugin.IInputDataSource;
import com.bibibiradio.input.plugin.cmdline.CmdLineDataSource;
import com.bibibiradio.input.plugin.database.MysqlDataSource;
import com.bibibiradio.input.plugin.xlburpproxy.XlBurpProxyInputData;
import com.bibibiradio.scan.plugin.IScanPlugin;
import com.bibibiradio.scan.plugin.IVulnItem;
import com.bibibiradio.scan.plugin.cc.CcScanPlugin;
import com.bibibiradio.scan.plugin.headerinject.HeaderInjectScanPlugin;
import com.bibibiradio.scan.plugin.sensitive.SensitiveScanPlugin;

public class ScanManager {
	static private Logger logger = Logger.getLogger(ScanManager.class);
	
	static private IInputDataSource inputDataSource= null;
	static private ArrayList<IScanPlugin> scanPlugins = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		run(args);
	}
	
	public static void run(String[] args){
		String configPath = args[0];
		Map<String,Object> config = ConfigReader.formatConfig(configPath);
		config.put("cmdLine", args);
		
		if(!initInputPlugin(config)){
			return;
		}
		
		if(!initScanCore(config)){
			return;
		}
		
		List<IVulnItem> vulnItems = runScanCore();
		
		if(vulnItems == null){
			return;
		}
		
//		for(IVulnItem vulnItem:vulnItems){
//			System.out.println(vulnItem.getType()+" "+vulnItem.getPos()+" "+vulnItem.getDetail()+" "+vulnItem.getUrl());
//		}
		
		if(vulnItems.size() == 0){
			//System.out.println("no vuln");
			logger.info("no vuln");
		}
	}
	
	static private boolean initInputPlugin(Map<String,Object> config){
		@SuppressWarnings("unchecked")
		Map<String,String> inputPlugin = (Map<String, String>) config.get("inputPlugin");
		if(inputPlugin == null){
			return false;
		}
		if(inputPlugin.get("name").equals("cmdline")){
			inputDataSource = new CmdLineDataSource();
			inputDataSource.init(config);
		}else if(inputPlugin.get("name").equals("database")){
			inputDataSource = new MysqlDataSource();
			inputDataSource.init(config);
		}
		return true;
	}
	
	static private boolean initScanCore(Map<String,Object> config){
		List<HashMap<String,String>> scanPluginConfigs = (List<HashMap<String, String>>) config.get("scanPlugins");
		if(scanPluginConfigs == null || scanPluginConfigs.size() <= 0){
			return false;
		}
		
		scanPlugins = new ArrayList<IScanPlugin>();
		for(Map<String,String> scanPluginConfig:scanPluginConfigs){
			if(scanPluginConfig.get("name").equals("sensitive")){
				IScanPlugin sensitiveScanPlugin = new SensitiveScanPlugin();
				sensitiveScanPlugin.open("");
				
				scanPlugins.add(sensitiveScanPlugin);
			}else if(scanPluginConfig.get("name").equals("cc")){
				IScanPlugin ccScanPlugin = new CcScanPlugin();
				ccScanPlugin.open("");
				
				scanPlugins.add(ccScanPlugin);
			}else if(scanPluginConfig.get("name").equals("headerInject")){
                IScanPlugin headerInjectScanPlugin = new HeaderInjectScanPlugin();
                headerInjectScanPlugin.open("");
                
                scanPlugins.add(headerInjectScanPlugin);
            }
		}
		return true;
	}
	
	static private List<IVulnItem> runScanCore(){
		if(inputDataSource == null || scanPlugins == null){
			return null;
		}
		IInputData[] inputDatas = null;
		ArrayList<IVulnItem> vulnItems = new ArrayList<IVulnItem>();
		
		
		while(true){
			inputDatas = inputDataSource.getFromSource();
			if(inputDatas == null){
				break;
			}
			
			//System.out.println("��"+inputDatas.length+"ɨ��url");
			for(int i=0;i<inputDatas.length;i++){
				IInputData inputData = inputDatas[i];
				
				//System.out.println("����ɨ���"+i+"��ɨ��url "+inputData.getUrl());
				for(IScanPlugin scanPlugin:scanPlugins){
				    IVulnItem[] scanVulnItems = null;
				    
				    try{
				        scanVulnItems = scanPlugin.scan(inputData);
				    }catch(Exception ex){
				        logger.error("error message",ex);
				    }
				    
					if(scanVulnItems == null){
						continue;
					}
					
					for(int j=0;j<scanVulnItems.length;j++){
						//System.out.println("println "+scanVulnItems[j].getType()+" "+scanVulnItems[j].getPos()+" "+scanVulnItems[j].getDetail()+" "+scanVulnItems[j].getUrl());
						logger.info(scanVulnItems[j].getType()+" "+scanVulnItems[j].getPos()+" "+scanVulnItems[j].getDetail()+" "+scanVulnItems[j].getUrl());
						vulnItems.add(scanVulnItems[j]);
					}
				}
			}
		}
		
		
		if(vulnItems == null || vulnItems.size() < 0){
			return null;
		}
		
		return vulnItems;
	}
}
