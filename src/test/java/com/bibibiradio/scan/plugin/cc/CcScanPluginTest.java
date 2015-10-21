package com.bibibiradio.scan.plugin.cc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bibibiradio.input.plugin.xlburpproxy.XlBurpProxyInputData;

public class CcScanPluginTest {
	private static CcScanPlugin csp = null;
	@Before
	public void setUp() throws Exception {
		if(csp == null){
			csp = new CcScanPlugin();
			csp.open("abc");
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testScan() {
		//fail("Not yet implemented");
		XlBurpProxyInputData ipd = new XlBurpProxyInputData();
		ipd.setMethod("GET");
		ipd.setUrl("https://www.baidu.com/s?wd=%E7%BD%91%E7%BB%9C%E6%B5%8B%E9%80%9F&rsv_spt=1&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=9&rsv_sug1=7");
		csp.scan(ipd);
		ipd.setMethod("GET");
		ipd.setUrl("https://www.baidu.com/s?wd=good&rsv_spt=1&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_pq=f09a770c0000155e&rsv_t=6c85rhqV1RihMV%2BjCSxZffPjLnTsgEIOMX94VHDvHUjU6FUf6o4TcTS6kZYp8kuUzP3%2B&inputT=4225&rsv_sug3=20&rsv_sug1=20&rsv_sug2=0&rsv_sug4=4225");
		csp.scan(ipd);
		assertTrue(true);
	}

}
