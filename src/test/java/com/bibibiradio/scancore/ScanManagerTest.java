package com.bibibiradio.scancore;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bibibiradio.scancore.ScanManager;

public class ScanManagerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRun() {
//		String[] args = new String[]{"testdata/testconfig.xml","GET","http://www.test.checkhtml.com/id_card_sensitive"};
//		ScanManager.run(args);
		assertTrue(true);
		//fail("Not yet implemented");
	}
	
	@Test
	public void testRun2() {
		String[] args = new String[]{"testdata/testconfig2.xml","GET","http://www.test.checkhtml.com/id_card_sensitive"};
		ScanManager.run(args);
		assertTrue(true);
		//fail("Not yet implemented");
	}

}
