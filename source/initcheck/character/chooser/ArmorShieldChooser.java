package initcheck.character.chooser;

import initcheck.database.Armor;
import initcheck.database.ArmorDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class ArmorShieldChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public ArmorShieldChooser() {
		super();
		ArmorDAO db = new ArmorDAO();
		Armor a = new Armor();
		a.setType("ARMOR");
		a.setBonus(null);
		a.setArmorbonus(null);
		a.setSpeed20(null);
		a.setSpeed30(null);
		a.setDisplayType("ARMOR");
		a.setWeight(null);
		a.setCheckpenalty(null);

		Vector<Armor> v = db.selectArmor(a);

		a = new Armor();
		a.setType("SHIELD");
		a.setBonus(null);
		a.setArmorbonus(null);

		v.addAll(db.selectArmor(a));

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
