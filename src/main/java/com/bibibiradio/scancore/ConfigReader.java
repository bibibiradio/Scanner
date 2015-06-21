package com.bibibiradio.scancore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ConfigReader {
	
	public static Map<String,Object> formatConfig(String configPath){
		HashMap<String,Object> config = new HashMap<String,Object>();
		File configFile = new File(configPath);
		Document doc = null;
		if(configFile.exists() == false){
			return null;
		}
		
		try {
			doc = Jsoup.parse(configFile, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		Elements inputPlugins = doc.getElementsByTag("inputPlugin");
		HashMap<String,String> inputPluginConfig = new HashMap<String,String>();
		if(inputPlugins == null){
			return null;
		}
		for(Element inputPlugin:inputPlugins){
			inputPluginConfig.put("name", inputPlugin.getElementsByTag("name").get(0).text());
		}
		config.put("inputPlugin", inputPluginConfig);
		
		Elements outputPlugins = doc.getElementsByTag("outputPlugin");
		HashMap<String,String> outputPluginConfig = new HashMap<String,String>();
		if(outputPlugins == null){
			return null;
		}
		for(Element outputPlugin:outputPlugins){
			outputPluginConfig.put("name",outputPlugin.getElementsByTag("name").get(0).text());
		}
		config.put("outputPlugin", outputPluginConfig);
		
		ArrayList<HashMap<String,String>> scanPluginList = new ArrayList<HashMap<String,String>>();
		Elements scanPlugins = doc.getElementsByTag("scanPlugin");
		if(scanPlugins == null){
			return null;
		}
		for(Element scanPlugin:scanPlugins){
			HashMap<String,String> scanConfig = new HashMap<String,String>();
			
			scanConfig.put("name", scanPlugin.getElementsByTag("name").get(0).text());
			scanPluginList.add(scanConfig);
		}
		config.put("scanPlugins", scanPluginList);
		
		return config;
	}
}
