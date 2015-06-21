package com.bibibiradio.input.plugin;

import com.bibibiradio.scan.plugin.IInputData;

public interface IInputDataSource {
	public boolean init(String config);
	public void close();
	public IInputData[] getFromSource();
}
