package initcheck.character;

import initcheck.PlayerManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ManagerPopupMenu extends JPopupMenu implements ActionListener {

	// the popup menu

	private static final long serialVersionUID = 1L;

	private JMenuItem addxp;

	private JMenuItem changeParty;

	private PlayerManager owner;

	public ManagerPopupMenu(PlayerManager owner) {

		super();

		this.owner = owner;

		addxp = new JMenuItem("Add XP");
		addxp.addActionListener(this);
		add(addxp);

		changeParty = new JMenuItem("Change Party");
		changeParty.addActionListener(this);
		add(changeParty);
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());

		if (source == addxp) {
			owner.addExp();
		} else if (source == changeParty) {
			owner.changeParty();
		}

	}
}
