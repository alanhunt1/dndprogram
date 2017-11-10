package initcheck.character.chooser;

import initcheck.database.Spell;
import initcheck.database.SpellDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class PotionSpellChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	private static Vector<Spell> v = null;

	public PotionSpellChooser(String cclass) {
		super();

		if (v == null) {
			SpellDAO db = new SpellDAO();
			v = db.getSpells("0", cclass);
			v.addAll(db.getSpells("1", cclass));
			v.addAll(db.getSpells("2", cclass));
			v.addAll(db.getSpells("3", cclass));
		}
		for (int i = 0; i < v.size(); i++) {
			Spell f = (Spell) v.get(i);
			addItem(f);
		}
	}

	public void setPotionClass(String cclass) {
		removeAllItems();

		SpellDAO db = new SpellDAO();
		v = db.getSpells("0", cclass);
		v.addAll(db.getSpells("1", cclass));
		v.addAll(db.getSpells("2", cclass));
		v.addAll(db.getSpells("3", cclass));

		for (int i = 0; i < v.size(); i++) {
			Spell f = (Spell) v.get(i);
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
