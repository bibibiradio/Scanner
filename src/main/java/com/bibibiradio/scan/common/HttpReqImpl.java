package com.bibibiradio.scan.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpReqImpl implements IHttpReq,Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String url;
    private String postBody;
    private Map<String,String> reqHeader;
    
    private String query;
    private String cookie;
    
    
    private HttpParams queryParams;
    private HttpParams postParams;
    private HttpParams cookieParams;
    
    public HttpReqImpl(){
        reqHeader = new HashMap<String,String>();
        queryParams = new HttpParams();
        postParams = new HttpParams();
        cookieParams = new HttpParams();
    }
    
    public HttpReqImpl deepClone() throws Exception{
        
        ByteArrayOutputStream bo=new ByteArrayOutputStream(); 
        ObjectOutputStream oo=new ObjectOutputStream(bo); 
        oo.writeObject(this); 
            
        ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray()); 
        ObjectInputStream oi=new ObjectInputStream(bi); 
        return (HttpReqImpl)(oi.readObject());
       
    }
    
    public boolean setUrlSyn(String url) throws Exception{
        URL uurl = new URL(url);
        this.url = url;
        query = uurl.getQuery();
        queryParams.setParams(query);
        queryParams.synFromParams();
        return true;
    }
    
    public boolean setPostBodySyn(String postBody) throws Exception{
    	if(postBody == null){
    		return false;
    	}
        this.postBody = postBody;
        postParams.setParams(postBody);
        postParams.synFromParams();
        return true;
    }
    
    public boolean setReqHeaderSyn(Map<String,String> reqHeader) throws Exception{
    	if(reqHeader == null){
    		return false;
    	}
        this.reqHeader = reqHeader;
        cookie = reqHeader.get("Cookie");
        cookieParams.setItemConnector(";");
        cookieParams.setParams(cookie);
        cookieParams.synFromParams();
        return true;
    }
    
    public boolean setQueryItemSyn(String key,String value) throws Exception{
        URL uurl1 = new URL(url);
        
        Map<String,String> queryKeyValues = queryParams.getKeyValues();
        if(queryKeyValues == null){
            return false;
        }
        queryKeyValues.put(key, value);
        queryParams.synFromKeyValues();
        query = queryParams.getParams();
        
        String file = uurl1.getFile();
        String[] querys = file.split("\\?");
        if(querys.length<2){
            return false;
        }
        file = querys[0]+"?"+query;
        URL uurl2 = new URL(uurl1.getProtocol(),uurl1.getHost(),file);
        
        url = uurl2.toString();
        return true;
    }
    
    public boolean setPostItemSyn(String key,String value) throws Exception{
        Map<String,String> postKeyValues = postParams.getKeyValues();
        if(postKeyValues == null){
            return false;          
        }
        postKeyValues.put(key, value);
        postParams.synFromKeyValues();
        postBody = postParams.getParams();
        return true;
    }
    
    public boolean setCookieItemSyn(String key,String value) throws Exception{
        Map<String,String> cookieKeyValues = cookieParams.getKeyValues();
        if(cookieKeyValues == null){
            return false;
        }
        cookieKeyValues.put(key, value);
        cookieParams.synFromKeyValues();
        cookie = cookieParams.getParams();
        reqHeader.put("Cookie", cookie);
        return true;
    }
    
    public Iterator<Entry<String, String>> getQueryIter(){
        Map<String,String> queryKeyValues = queryParams.getKeyValues();
        if(queryKeyValues == null){
            return null;
        }
        
        return queryKeyValues.entrySet().iterator();
    }
    
    public Iterator<Entry<String, String>> getPostIter(){
        Map<String,String> postKeyValues = postParams.getKeyValues();
        if(postKeyValues == null){
            return null;
        }
        
        return postKeyValues.entrySet().iterator();
    }
    
    public Iterator<Entry<String, String>> getCookieIter(){
        Map<String,String> cookieKeyValues = cookieParams.getKeyValues();
        if(cookieKeyValues == null){
            return null;
        }
        
        return cookieKeyValues.entrySet().iterator();
    }
    
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getPostBody() {
        return postBody;
    }
    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
    public Map<String, String> getReqHeader() {
        return reqHeader;
    }
    public void setReqHeader(HashMap<String, String> reqHeader) {
        this.reqHeader = reqHeader;
    }
    public String getCookie() {
        return cookie;
    }
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
    public HttpParams getQueryParams() {
        return queryParams;
    }
    public void setQueryParams(HttpParams queryParams) {
        this.queryParams = queryParams;
    }
    public HttpParams getPostParams() {
        return postParams;
    }
    public void setPostParams(HttpParams postParams) {
        this.postParams = postParams;
    }

    public HttpParams getCookieParams() {
        return cookieParams;
    }

    public void setCookieParams(HttpParams cookieParams) {
        this.cookieParams = cookieParams;
    }

    public void setReqHeader(Map<String, String> reqHeader) {
        this.reqHeader = reqHeader;
    }
    
}
