package com.bibibiradio.input.plugin.cmdline;

import java.util.Map;

import com.bibibiradio.input.plugin.IInputData;
import com.bibibiradio.input.plugin.IInputDataSource;

public class CmdLineDataSource implements IInputDataSource {
	private CmdLineInputData cmdLineInputData = null;
	private boolean isOver = false;
	@Override
	public boolean init(Map<String, Object> inputPluginConfig) {
		// TODO Auto-generated method stub
		String[] args = (String[]) inputPluginConfig.get("cmdLine");
		cmdLineInputData = new CmdLineInputData();
		cmdLineInputData.setUrl(args[2]);
		cmdLineInputData.setMethod(args[1]);
		return true;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public IInputData[] getFromSource() {
		// TODO Auto-generated method stub
		if(isOver == false){
			isOver = true;
			return new IInputData[]{cmdLineInputData};
		}
		return null;
	}

}
