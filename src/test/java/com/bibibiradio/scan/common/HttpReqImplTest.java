package com.bibibiradio.scan.common;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bibibiradio.scan.common.HttpReqImpl;

public class HttpReqImplTest {
    private static Logger logger = Logger.getLogger(HttpReqImplTest.class);
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testNoQuery() {
       HttpReqImpl hr = new HttpReqImpl();
       
       try {
           hr.setUrlSyn("http://www.baidu.com/path");
           hr.setQueryItemSyn("name","kkk");
           logger.info(hr.getUrl());
       } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("error message",e);
       }
    }
    
    @Test
    public void test3Query() {
       HttpReqImpl hr = new HttpReqImpl();
       
       try {
           hr.setUrlSyn("http://www.baidu.com/path?name=xiaolei&password=123&age=3");
           hr.setQueryItemSyn("name","kkk");
           logger.info(hr.getUrl());
       } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("error message",e);
       }
    }

}
