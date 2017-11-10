package initcheck.character.chooser;

import initcheck.database.Spell;
import initcheck.database.SpellDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class SpellChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	private static Vector<Spell> v = null;

	public SpellChooser() {
		super();

		if (v == null) {
			SpellDAO db = new SpellDAO();
			v = db.getSpells();
		}
		for (int i = 0; i < v.size(); i++) {
			Spell f = (Spell) v.get(i);
			addItem(f);
		}
	}

	public SpellChooser(String level, String cclass) {
		super();
		SpellDAO db = new SpellDAO();

		Vector<Spell> v2 = db.getSpells(level, cclass);
		for (int i = 0; i < v2.size(); i++) {
			Spell f = (Spell) v2.get(i);
			addItem(f);
		}
	}

	public void setSelectedSpell(Spell s) {
		for (int i = 0; i < getItemCount(); i++) {
			Spell sk = (Spell) getItemAt(i);
			if (s.getId().equals(sk.getId())) {
				setSelectedIndex(i);
				break;
			}
		}
	}
}
