package com.bibibiradio.scan.common;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bibibiradio.scan.common.HttpParams;

public class HttpParamsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        String testString = "name=xl&pwd=123456";
        HttpParams httpParams = new HttpParams();
        try{
            assertTrue(!httpParams.syn());
            
            httpParams.setParams(testString);
            assertTrue(httpParams.syn());
            Map<String,String> kvs = httpParams.getKeyValues();
            assertTrue(kvs.size() == 2);
            assertTrue(kvs.get("name").equals("xl") && kvs.get("pwd").equals("123456"));
            
            httpParams = new HttpParams();
            httpParams.setKeyValues(kvs);
            assertTrue(httpParams.getParams().equals("name=xl&pwd=123456"));
        }catch(Exception ex){
            
        }
    }

}
