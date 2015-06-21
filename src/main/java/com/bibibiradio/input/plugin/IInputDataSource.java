package com.bibibiradio.input.plugin;

import java.util.Map;

public interface IInputDataSource {
	public boolean init(Map<String,Object> inputPluginConfig);
	public void close();
	public IInputData[] getFromSource();
}
