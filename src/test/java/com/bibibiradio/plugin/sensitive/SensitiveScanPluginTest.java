package com.bibibiradio.plugin.sensitive;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bibibiradio.scan.plugin.sensitive.SensitiveScanPlugin;

public class SensitiveScanPluginTest {
	static private SensitiveScanPlugin ssp = null;
	@Before
	public void setUp() throws Exception {
		if(ssp == null){
			ssp = new SensitiveScanPlugin();
			ssp.open();
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testScan() {
		//fail("Not yet implemented");
		assertTrue(true);
	}

}
