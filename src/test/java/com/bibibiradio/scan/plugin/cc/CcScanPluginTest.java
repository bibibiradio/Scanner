package com.bibibiradio.scan.plugin.cc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		csp.scan(null);
		assertTrue(true);
	}

}
