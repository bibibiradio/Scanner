package com.bibibiradio.input.plugin.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.bibibiradio.input.plugin.IInputData;
import com.bibibiradio.input.plugin.IInputDataSource;


public class MysqlDataSource implements IInputDataSource {
	private String dbConfigPath = null;
	private static SqlSessionFactory sqlSessionFactory;
	private static long maxItemId = -1;
	private static long currentItemId = 0;
	@Override
	public boolean init(Map<String, Object> config) {
		// TODO Auto-generated method stub
		Map<String,String> inputPluginConfig = (Map<String, String>) config.get("inputPlugin");
		if(inputPluginConfig == null){
			return false;
		}
		
		dbConfigPath = inputPluginConfig.get("config");
		if(dbConfigPath == null){
			return false;
		}
		
		try {
			sqlSessionFactory =  new SqlSessionFactoryBuilder().build(new FileInputStream(dbConfigPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public IInputData[] getFromSource() {
		// TODO Auto-generated method stub
		List<MysqlScanItem> mysqlScanItems = null;
		
		if(maxItemId == -1){
			maxItemId = getMaxItemId();
		}
		
		if(currentItemId >= maxItemId){
			return null;
		}
		
		mysqlScanItems = getScanItems(currentItemId);
		
		if(mysqlScanItems == null || mysqlScanItems.size()<=0){
			return null;
		}
		
		MysqlInputData[] mysqlInputDatas = new MysqlInputData[mysqlScanItems.size()];
		
		int i = 0;
		for(MysqlScanItem mysqlScanItem:mysqlScanItems){
			MysqlInputData inputData = new MysqlInputData();
			inputData.setMethod(mysqlScanItem.getMethod());
			String url = mysqlScanItem.getUrl();
			url = url.replace("http//", "http://");
			url = url.replace("https//", "https://");
			inputData.setUrl(url);
			inputData.setReqBody(mysqlScanItem.getRequestOrig());
			//inputData.setReqHeader(reqHeader);
			//inputData.setResHeader(resHeader);
			inputData.setResBody(mysqlScanItem.getResponseOrig());
			setIsScan(mysqlScanItem.getItemId());
			mysqlInputDatas[i] = inputData;
			i++;
			if(i == mysqlScanItems.size()){
				currentItemId = mysqlScanItem.getItemId();
			}
		}
		
		
		return mysqlInputDatas;
	}
	
	private long getMaxItemId(){
		
		SqlSession session = sqlSessionFactory.openSession();
		
		MysqlScanItem resultItem = null;
		MysqlScanItem selectItem = new MysqlScanItem();
		selectItem.setItemId(0);
        
	    try {
	    	resultItem = (MysqlScanItem) session.selectOne("com.bibibiradio.input.plugin.database。MysqlDataSource.getMaxItemId", selectItem);
	    } finally {
	    	session.commit();
	        session.close();
	    }
	        
	   return resultItem.getItemId();
	}
	
	private List<MysqlScanItem> getScanItems(long currentItemId){
		SqlSession session = sqlSessionFactory.openSession();
		
		MysqlScanItem selectItem = new MysqlScanItem();
		selectItem.setItemId(currentItemId);
		
		try {
	    	return session.selectList("com.bibibiradio.input.plugin.database。MysqlDataSource.getScanItems", selectItem);
	    } finally {
	    	session.commit();
	        session.close();
	    }
	}
	
	private boolean setIsScan(long itemId){
		SqlSession session = sqlSessionFactory.openSession();
		
		MysqlScanItem selectItem = new MysqlScanItem();
		selectItem.setItemId(itemId);
		
		try {
	    	session.update("com.bibibiradio.input.plugin.database。MysqlDataSource.updateIsScan", itemId);
	    } finally {
	    	session.commit();
	        session.close();
	    }
		
		return true;
	}

}
