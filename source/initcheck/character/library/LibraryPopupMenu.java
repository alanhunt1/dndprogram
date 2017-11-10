package initcheck.character.library;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class LibraryPopupMenu extends JPopupMenu implements ActionListener {

	// the popup menu

	private static final long serialVersionUID = 1L;

	private JMenuItem applyMods;

	private JMenuItem applyItems;

	private JMenuItem upgrade;

	private LibraryPanel owner;

	public LibraryPopupMenu(LibraryPanel owner) {

		super();

		this.owner = owner;

		applyMods = new JMenuItem("Export");
		applyMods.addActionListener(this);
		add(applyMods);

	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());

		if (source == applyMods) {
			owner.exportAll();
		} else if (source == applyItems) {
			// owner.applyFeats();
		} else if (source == upgrade) {
			// owner.upgrade();
		}

	}
}
