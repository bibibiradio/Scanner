package com.bibibiradio.scan.common;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpParams {
    private Map<String,String> keyValues;
    private String params;
    private String keyValuesConnector;
    private String itemConnector;
    private String valueDecoder;
    private String charDecoder;
    
    
    
    public HttpParams(){
        keyValuesConnector = "=";
        itemConnector = "&";
        valueDecoder = "URL";
        charDecoder = "UTF-8";
    }
    
    public boolean syn() throws Exception{
        if(params == null && keyValues == null){
            return false;
        }
        
        if(params == null){
            params = getParamsFromKeyValues(keyValues);
            return true;
        }
        
        if(keyValues == null){
            keyValues = getKeyValuesFromParams(params);
        }
        
        return true;
    }
    
    public boolean synFromKeyValues() throws Exception{
        params = getParamsFromKeyValues(keyValues);
        return true;
    }
    
    public boolean synFromParams() throws Exception{
        keyValues = getKeyValuesFromParams(params);
        return true;
    }
    
    private String getParamsFromKeyValues(Map<String,String> keyValues) throws Exception{
        Iterator<Entry<String, String>> iter = keyValues.entrySet().iterator();
        StringBuilder strBuilder = new StringBuilder("");
        
        while(iter.hasNext()){
            Entry<String,String> entry = iter.next();
            strBuilder.append(entry.getKey());
            strBuilder.append(keyValuesConnector);
            
            String encoderValue = null;
            if(valueDecoder.equals("URL")){
                encoderValue = URLEncoder.encode(entry.getValue(), charDecoder);
            }else{
                encoderValue = entry.getValue();
            }
            
            strBuilder.append(encoderValue);
            strBuilder.append(itemConnector);
        }
        
        strBuilder.deleteCharAt(strBuilder.length()-1);
        
        return strBuilder.toString();
        
    }
    
    private Map<String,String> getKeyValuesFromParams(String params) throws Exception{
        if(params == null){
            return null;
        }
        
        HashMap<String,String> result = new HashMap<String,String>();
        
        String[] items = params.split(itemConnector);
        if(items == null || items.length == 0){
            return result;
        }
        
        for(String item : items){
            String[] keyValue = item.split(keyValuesConnector);
            
            if(keyValue.length < 2){
            	return null;
            }
            
            String decoderValue = null;
            if(valueDecoder.equals("URL")){
                decoderValue = URLDecoder.decode(keyValue[1], charDecoder);
            }else{
                decoderValue = keyValue[1];
            }
            
            result.put(keyValue[0], decoderValue);
        }
        
        return result;
    }

    public Map<String, String> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(Map<String, String> keyValues) {
        this.keyValues = keyValues;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getKeyValuesConnector() {
        return keyValuesConnector;
    }

    public void setKeyValuesConnector(String keyValuesConnector) {
        this.keyValuesConnector = keyValuesConnector;
    }

    public String getItemConnector() {
        return itemConnector;
    }

    public void setItemConnector(String itemConnector) {
        this.itemConnector = itemConnector;
    }

    public String getValueDecoder() {
        return valueDecoder;
    }

    public void setValueDecoder(String valueDecoder) {
        this.valueDecoder = valueDecoder;
    }

    public String getCharDecoder() {
        return charDecoder;
    }

    public void setCharDecoder(String charDecoder) {
        this.charDecoder = charDecoder;
    }
}
