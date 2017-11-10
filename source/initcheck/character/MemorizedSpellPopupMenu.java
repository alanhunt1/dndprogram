package initcheck.character;

import initcheck.DCharacter;
import initcheck.database.Feat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MemorizedSpellPopupMenu extends JPopupMenu {

	// the popup menu

	private static final long serialVersionUID = 1L;

	private PlayerSpellPanel owner;

	Vector<Feat> mmf = new Vector<Feat>();

	public MemorizedSpellPopupMenu(PlayerSpellPanel owner) {

		super();

		this.owner = owner;

		DCharacter dc = owner.getChar();
		mmf = dc.getMetaMagicFeats();
		for (int i = 0; i < mmf.size(); i++) {

			final Feat feat = (Feat) mmf.get(i);
			JMenuItem metaItem = new JMenuItem(feat.getFeatName());
			metaItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					adjustFeat(feat);
				}
			});
			add(metaItem);
		}

	}

	public void adjustFeat(Feat f) {
		owner.adjustSpell(f);
	}
}
