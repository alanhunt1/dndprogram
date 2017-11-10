package initcheck.client;

import initcheck.InitClient;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

public class PlayerDamageDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public PlayerDamageDialog(PlayerDamagePanel pdp, final InitClient owner) {

		add(pdp);
		pack();
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				owner.hideHitSheetDialog();
				dispose();
			}
		});
	}

}
