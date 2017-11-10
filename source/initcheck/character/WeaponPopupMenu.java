package initcheck.character;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class WeaponPopupMenu extends JPopupMenu implements ActionListener {

	// the popup menu

	private static final long serialVersionUID = 1L;

	private JMenuItem applyMods;

	private JMenuItem applyItems;

	private JMenuItem upgrade;

	private PlayerWeaponPanel owner;

	public WeaponPopupMenu(PlayerWeaponPanel owner) {

		super();

		this.owner = owner;

		applyMods = new JMenuItem("Apply Mods");
		applyMods.addActionListener(this);
		add(applyMods);
		applyItems = new JMenuItem("Apply Feats");
		applyItems.addActionListener(this);
		add(applyItems);
		upgrade = new JMenuItem("Upgrade");
		upgrade.addActionListener(this);
		add(upgrade);

	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());

		if (source == applyMods) {
			owner.applyMods();
		} else if (source == applyItems) {
			owner.applyFeats();
		} else if (source == upgrade) {
			owner.upgrade();
		}

	}
}
