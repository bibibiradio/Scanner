package burp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;

import com.bibibiradio.burp.httpproxy.HttpPackageRecordor;

public class BurpExtender implements IBurpExtender,IProxyListener {
	static private IBurpExtenderCallbacks callback = null;
	static private HttpPackageRecordor httpPackageRecordor = null;
	static private OutputStream output = null;
	
	@Override
	public void registerExtenderCallbacks(IBurpExtenderCallbacks callback) {
		// TODO Auto-generated method stub
		this.callback = callback;
		OutputStream output = callback.getStderr();
		this.output = output;
		httpPackageRecordor = new HttpPackageRecordor(callback);
		
		callback.setExtensionName("Customer Http Proxy");
		callback.registerProxyListener(this);
	}

	@Override
	public void processProxyMessage(boolean messageIsRequest, IInterceptedProxyMessage message) {
		// TODO Auto-generated method stub
		httpPackageRecordor.record(messageIsRequest, message);
	}

}
