package initcheck.preferences;

import initcheck.InitBase;
import initcheck.character.GridPanel;

import java.io.Serializable;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class ProxyPanel extends GridPanel implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private JCheckBox useProxy = new JCheckBox("Use Proxy");
	
	private JTextField proxyAddress = new JTextField(30);
	
	private JTextField proxyPort = new JTextField(4);
	
	private InitBase owner;
	
	public ProxyPanel(final InitBase owner) {
		
		doLayout(useProxy, 0);
		doLayout(proxyAddress, 1);
		doLayout(proxyPort, 2);
		
		this.owner = owner;
	}
	
	public void save(){
		if (useProxy.isSelected()){
			owner.setProxy(proxyAddress.getText());
			owner.setProxyPort(proxyPort.getText());
		}else{
			owner.setProxy(null);
		}
		owner.writePrefsToFile();
	}

	public boolean getUseProxy() {
		return useProxy.isSelected();
	}

	public void setUseProxy(boolean useProxy) {
		this.useProxy.setSelected(useProxy);
	}

	public String getProxyAddress() {
		return proxyAddress.getText();
	}

	public void setProxyAddress(String proxyAddress) {
		this.proxyAddress.setText(proxyAddress);
	}

	public String getProxyPort() {
		return proxyPort.getText();
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort.setText(proxyPort);
	}
	
	
}
