package com.bibibiradio.burp.httpproxy.dbinsert;

import java.io.FileInputStream;
import java.io.Reader;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;

public class ScanItemInsertor {
	private static SqlSessionFactory sqlSessionFactory;
	private static String sqlMapConfigPath=null;
	
	public ScanItemInsertor(String sqlMapConfigPath){
		try{
			ScanItemInsertor.sqlMapConfigPath=sqlMapConfigPath;
			Reader reader;
			
			if(sqlSessionFactory==null){
				//com.ibatis.common.resources.Resources.setDefaultClassLoader(getClass().getClassLoader());
				//reader = Resources.getUrlAsReader(sqlMapConfigPath);
				FileInputStream configInput = new FileInputStream(sqlMapConfigPath);
				//reader = getClass().getClassLoader().getResourceAsStream(ScanItemInsertor.sqlMapConfigPath);
				if(configInput != null){
					sqlSessionFactory = new SqlSessionFactoryBuilder().build(configInput);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public ScanItemInsertor(){}
	
	public long insert(ScanItem item){
		if(item == null){
			return -1;
		}
		SqlSession session = sqlSessionFactory.openSession();
        
        try {
            return session.insert("com.bibibiradio.burp.httpproxy.dbinsert.ScanItem.insertScanItem", item);
        } finally {
        	session.commit();
            session.close();
        }
	}
	
	public ScanItem selectScanItemByHash(ScanItem item){
//		if(selectData==null){
//			return null;
//		}
		SqlSession session = sqlSessionFactory.openSession();
        
		ScanItem result = null;
	    try {
	        result = (ScanItem) session.selectOne("com.bibibiradio.burp.httpproxy.dbinsert.ScanItem.getScanItemByHash", item);
	    } finally {
	        session.close();
	    }
	        
	   return result;
	}
	
	public boolean deleteAll(){
		// TODO Auto-generated method stub
        SqlSession session = sqlSessionFactory.openSession();
        
        try {
            session.delete("com.bibibiradio.burp.httpproxy.dbinsert.ScanItem.deleteScanItems");
        } finally {
        	session.commit();
            session.close();
        }
		
		return true;
	}
	
	
}
