package com.bibibiradio.scan.plugin;

public interface IScanPlugin {
	public boolean open(String config);
	public IVulnItem[] scan(IInputData inputData);
	public void close();
}
