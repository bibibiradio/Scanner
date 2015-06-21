package com.bibibiradio.scan.plugin;

public interface IScanPlugin {
	public boolean open();
	public IVulnItem[] scan(IInputData inputData);
	public void close();
}
