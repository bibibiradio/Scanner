package com.bibibiradio.input.plugin.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.bibibiradio.input.plugin.IInputData;
import com.bibibiradio.input.plugin.IInputDataSource;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class MysqlDataSource implements IInputDataSource {
	private String dbConfigPath = null;
	private static SqlMapClient smc=null;
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
		
		com.ibatis.common.resources.Resources.setDefaultClassLoader(getClass().getClassLoader());
		try {
			smc = SqlMapClientBuilder.buildSqlMapClient(new FileInputStream(dbConfigPath));
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
		MysqlScanItem resultItem = null;
		MysqlScanItem selectItem = new MysqlScanItem();
		selectItem.setItemId(0);
		
		if(maxItemId == -1){
			try {
				startTransaction();
				resultItem = (MysqlScanItem) smc.queryForObject("getMaxItemId", selectItem);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				endTransaction();
			}
			maxItemId = resultItem.getItemId();
		}
		
		if(currentItemId >= maxItemId){
			return null;
		}
		
		selectItem.setItemId(currentItemId);
		try {
			startTransaction();
			mysqlScanItems = smc.queryForList("getScanItems", selectItem);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			endTransaction();
		}
		
		if(mysqlScanItems == null || mysqlScanItems.size()<0){
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
			
			mysqlInputDatas[i] = inputData;
			i++;
			if(i == mysqlScanItems.size()){
				currentItemId = mysqlScanItem.getItemId();
			}
		}
		
		
		return mysqlInputDatas;
	}
	
	private void endTransaction(){
		try {
			smc.commitTransaction();
			smc.endTransaction();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void startTransaction(){
		try {
			smc.startTransaction();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
