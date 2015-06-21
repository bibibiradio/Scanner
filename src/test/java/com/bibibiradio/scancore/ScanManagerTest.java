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
		String[] args = new String[]{"GET","http://www.test.checkhtml.com/id_card_sensitive"};
		ScanManager.run(args);
		assertTrue(true);
		//fail("Not yet implemented");
	}

}
