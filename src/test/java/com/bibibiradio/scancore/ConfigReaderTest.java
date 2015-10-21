package com.bibibiradio.scancore;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bibibiradio.scancore.ConfigReader;

public class ConfigReaderTest {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFormatConfig() {
		Map<String,Object> config = ConfigReader.formatConfig("testdata/testconfig.xml");
		assertTrue(((HashMap<String,String>)config.get("inputPlugin")).get("name").equals("cmdline"));
		assertTrue(((HashMap<String,String>)config.get("outputPlugin")).get("name").equals("console"));
		
		@SuppressWarnings("unchecked")
		List<HashMap<String,String>> scanPluginConfigs = (List<HashMap<String, String>>) config.get("scanPlugins");
		for(HashMap<String,String> scanPluginConfig:scanPluginConfigs){
			if(scanPluginConfig.get("name").equals("xss") || scanPluginConfig.get("name").equals("sensitive")){
				assertTrue(true);
			}
		}
		
		Map<String,Object> config2 = ConfigReader.formatConfig("testdata/testconfig2.xml");
		assertTrue(((HashMap<String,String>)config2.get("inputPlugin")).get("name").equals("database"));
		assertTrue(((HashMap<String,String>)config2.get("inputPlugin")).get("config").equals("testdata/SqlMapConfig.xml"));
		assertTrue(((HashMap<String,String>)config2.get("outputPlugin")).get("name").equals("console"));
		
		@SuppressWarnings("unchecked")
		List<HashMap<String,String>> scanPluginConfigs2 = (List<HashMap<String, String>>) config2.get("scanPlugins");
		for(HashMap<String,String> scanPluginConfig:scanPluginConfigs2){
			if(scanPluginConfig.get("name").equals("xss") || scanPluginConfig.get("name").equals("sensitive")){
				assertTrue(true);
			}
		}
	}

}
