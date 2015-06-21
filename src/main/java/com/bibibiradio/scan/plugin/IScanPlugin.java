package com.bibibiradio.scan.plugin;

import com.bibibiradio.input.plugin.IInputData;

public interface IScanPlugin {
	public boolean open(String config);
	public IVulnItem[] scan(IInputData inputData);
	public void close();
}
