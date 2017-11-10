package initcheck;

import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledPanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JPanel;

public class PlayerDialog extends TiledDialog {

	static final long serialVersionUID = 1;

	

	private PanelButton closeButton = new PanelButton("Close", 100);


	SelectPlayerPanel spp;
	
	
	public PlayerDialog(final PlayerDialogParent owner, Vector<DCharacter> chars) {

		super(owner.getFrame(), "Manage Players", true);

		spp = new SelectPlayerPanel(owner, chars);

	
		JPanel buttons = new TiledPanel(InitImage.lightRocks);
		buttons.add(closeButton);
		buttons.setOpaque(false);
		
		setButtonBar(buttons);
		setMainWindow(spp);

		setSize(new Dimension(550, 400));

		// Finish setting up the frame, and show it.
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

			}
		});

		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
			}
		});

		
	}
}
