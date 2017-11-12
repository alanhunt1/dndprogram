package initcheck.character.chooser;

import initcheck.database.Armor;
import initcheck.database.ArmorDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class ArmorChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	public ArmorChooser() {
		super();
		ArmorDAO db = new ArmorDAO();
		Vector<Armor> v = db.getArmorType("ARMOR");
		for (int i = 0; i < v.size(); i++) {
			Armor f = (Armor) v.get(i);
			addItem(f);
		}

	}

	public void setSelectedArmor(Armor w) {
		for (int i = 0; i < getItemCount(); i++) {
			Armor w2 = (Armor) getItemAt(i);
			if (w2.getId().equals(w.getId())) {
				setSelectedIndex(i);
				break;
			}
		}

	}
}
