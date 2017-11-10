package initcheck.character;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ItemPopupMenu extends JPopupMenu implements ActionListener {

	// the popup menu

	
	private static final long serialVersionUID = 1L;

	private JMenuItem backpack;

	private JMenuItem room;

	private JMenuItem potionbelt;

	private JMenuItem scrollorganizer;

	private PlayerItemPanel owner;

	public ItemPopupMenu(PlayerItemPanel owner) {

		super();

		this.owner = owner;

		backpack = new JMenuItem("Backpack");
		backpack.addActionListener(this);
		add(backpack);
		room = new JMenuItem("Room");
		room.addActionListener(this);
		add(room);
		potionbelt = new JMenuItem("Potion Belt");
		potionbelt.addActionListener(this);
		add(potionbelt);
		scrollorganizer = new JMenuItem("Scroll Organizer");
		scrollorganizer.addActionListener(this);
		add(scrollorganizer);
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());

		if (source == backpack) {
			owner.moveItems("Backpack");
		} else if (source == room) {
			owner.moveItems("Room");
		} else if (source == potionbelt) {
			owner.moveItems("Potion Belt");
		} else if (source == scrollorganizer) {
			owner.moveItems("Scroll Organizer");
		}

	}
}
