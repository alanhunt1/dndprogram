package initcheck.io;

import initcheck.InitFont;
import initcheck.PanelButton;
import initcheck.database.NetworkLog;
import initcheck.database.NetworkLogDAO;
import initcheck.graphics.TiledPanel;
import initcheck.utils.StrManip;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class NetworkMonitorDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	JTextArea mesg = new JTextArea("", 4, 80);
	
	public NetworkMonitorDialog(){
		super();
		init("Network Monitor");
	}
	
	public static void display() {
		@SuppressWarnings("unused")
		NetworkMonitorDialog md = new NetworkMonitorDialog();	
		//md.init("Network Monitor");	
	}
	
	private void init(String title) {
		PanelButton iconButton = new PanelButton("Close", 80);
		PanelButton refreshButton = new PanelButton("Refresh", 80);
		PanelButton clearButton = new PanelButton("Clear DB", 80);
		setTitle(title);

		TiledPanel contents = new TiledPanel();
		contents.setLayout(new BorderLayout());
		contents.setBorder(BorderFactory.createEmptyBorder(20, // top
				20, // left
				10, // bottom
				20) // right
				);
		
		iconButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNetworkData();
			}
		});
		
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearNetworkData();
			}
		});
		
		mesg.setOpaque(true);
		mesg.setLineWrap(true);
		mesg.setWrapStyleWord(true);
		mesg.setEditable(false);
		mesg.setFont(InitFont.courier);
		contents.add(mesg, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(iconButton);
		buttonPanel.add(refreshButton);
		buttonPanel.add(clearButton);
		contents.add(buttonPanel, BorderLayout.SOUTH);

		setNetworkData();
		
		getContentPane().add(contents);

		setSize(new Dimension(700, 450));
		setLocationRelativeTo(null);

		setVisible(true);

		iconButton.requestFocusInWindow();
	}
	
	private void clearNetworkData(){
		NetworkLogDAO nldb = new NetworkLogDAO();
		nldb.wipeLog();
		setNetworkData();
	}
	
	private void setNetworkData(){
		StringBuffer sbuf = new StringBuffer();
		NetworkLogDAO nldb = new NetworkLogDAO();
		Vector<NetworkLog> v = nldb.selectNetworkLog(new NetworkLog());
		for (NetworkLog nl : v){
			sbuf.append(StrManip.pad(nl.getOperation(), 25)+StrManip.pad(nl.getLagTime(), 5)+StrManip.pad(nl.getTaskTime(), 5)+StrManip.pad(nl.getSendTime(), 30));
			sbuf.append("\n");
		}
		
		mesg.setText(sbuf.toString());
	}

}
