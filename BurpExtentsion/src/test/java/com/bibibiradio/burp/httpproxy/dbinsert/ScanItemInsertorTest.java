package com.bibibiradio.burp.httpproxy.dbinsert;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ScanItemInsertorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ScanItemInsertor in = new ScanItemInsertor("/Users/xiaoleixl/projects/java/Scanner/BurpExtentsion/src/main/resources/SqlMapConfig.xml");
		
		in.deleteAll();
		
		ScanItem item = null;
		item = new ScanItem();
		item.setItemHash("234".getBytes());
		item.setInsertScanTime(new Date());
		item.setMethod("GET");
		item.setRequestContentType(1);
		item.setRequestOrig("abc".getBytes());
		item.setResponseStatusCode(2);
		item.setResponseContentType("text/html");
		item.setResponseOrig("xyz".getBytes());
		item.setUrl("http://qwe");
		
		System.out.println(in.insert(item));
	}

}
