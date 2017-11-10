package initcheck.server;

import initcheck.InitServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class NetworkPopupMenu extends JPopupMenu implements ActionListener {

	
	private static final long serialVersionUID = 1L;

	private JMenuItem clear;
	
	private InitServer owner;
	
	public NetworkPopupMenu(InitServer owner) {

		super();

		this.owner = owner;
		clear = new JMenuItem("Clear Traffic");
		clear.addActionListener(this);
		add(clear);
	}
	
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());

		if (source == clear) {
			owner.clearTraffic();
		}
	}
}
