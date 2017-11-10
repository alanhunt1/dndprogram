package initcheck.preferences;

import initcheck.InitBase;
import initcheck.PanelButton;
import initcheck.graphics.DefaultSkin;
import initcheck.graphics.NoSkin;
import initcheck.graphics.Skin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;

public class SkinsPanel extends JPanel implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private JList skinsList = new JList();
	
	private PanelButton closeButton = new PanelButton("OK", 70);
	
	private InitBase owner;
	
	public SkinsPanel(final InitBase owner) {
		setLayout(new BorderLayout());
		
		this.owner = owner;
		
		Vector<Skin> skins = new Vector<Skin>();
		skins.add(new NoSkin());
		skins.add(new DefaultSkin());
		
		skinsList.setListData(skins);
		
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSkin();
			}
		});
		add(skinsList, BorderLayout.CENTER);
		add(closeButton, BorderLayout.SOUTH);
	}
	
	private void setSkin(){
		owner.setSkin((Skin)skinsList.getSelectedValue());
	}
	
}
