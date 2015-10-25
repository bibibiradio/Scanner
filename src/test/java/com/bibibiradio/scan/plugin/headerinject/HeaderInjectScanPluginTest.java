package com.bibibiradio.scan.plugin.headerinject;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bibibiradio.input.plugin.xlburpproxy.XlBurpProxyInputData;
import com.bibibiradio.scan.plugin.IVulnItem;

public class HeaderInjectScanPluginTest {

    static private HeaderInjectScanPlugin ssp = null;
    @Before
    public void setUp() throws Exception {
        if(ssp == null){
            ssp = new HeaderInjectScanPlugin();
            ssp.open("abc");
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        XlBurpProxyInputData ipd = new XlBurpProxyInputData();
        ipd.setMethod("GET");
        ipd.setUrl("http://www.test.checkhtml.com/header_inject_yes?param=123");
        IVulnItem[] vulnItems = ssp.scan(ipd);
        
        assertTrue(vulnItems != null);
        
        for(int i=0;i<vulnItems.length;i++){
            System.out.println(vulnItems[i].getPos()+" "+vulnItems[i].getDetail());
        }
    }

}
