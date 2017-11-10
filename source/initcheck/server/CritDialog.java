package initcheck.server;

import initcheck.PanelButton;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class CritDialog extends JDialog {

	static final long serialVersionUID = 1;

	private ImageIcon bgImage = new ImageIcon("images/rock043.jpg");
	private JTextArea mesg = new JTextArea("", 4, 80);
	
	CritRoller owner;

	public static void showCritDialog(String title, String message, CritRoller owner){
		CritDialog c = new CritDialog(title, message, owner);
		c.setSize(new Dimension(300, 150));
		c.setLocation(100, 100);
		c.setVisible(true);
	}
	
	public CritDialog(String title, String message, CritRoller owner) {

		super();
		setTitle(title);
		this.owner = owner;

		PanelButton iconButton = new PanelButton("Close", 50);
		PanelButton againButton = new PanelButton("Roll Again", 100);

		TiledPanel contents = new TiledPanel(bgImage);
		contents.setLayout(new BorderLayout());
		contents.setBorder(BorderFactory.createEmptyBorder(20, // top
				20, // left
				10, // bottom
				20) // right
				);
		iconButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeUp();
			}
		});

		againButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMessage();
			}
		});

		mesg.setOpaque(true);
		mesg.setLineWrap(true);
		mesg.setWrapStyleWord(true);
		mesg.setText(message);

		contents.add(mesg, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(againButton);
		buttonPanel.add(iconButton);

		contents.add(buttonPanel, BorderLayout.SOUTH);

		getContentPane().add(contents);

		
	}

	private void closeUp() {
		owner.shutDialog();
		dispose();
	}

	public void setMessage(String s) {
		mesg.setText(s);
	}

	public void resetMessage() {
		setMessage(owner.getCrit());
	}

}
