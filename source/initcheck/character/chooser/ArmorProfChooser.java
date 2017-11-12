package initcheck.character.chooser;

import initcheck.database.Armor;
import initcheck.database.ArmorDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class ArmorProfChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	public ArmorProfChooser() {
		super();
		ArmorDAO db = new ArmorDAO();
		Armor a = new Armor();
		a.setType("ARMOR");
		a.setBonus(null);
		a.setArmorbonus(null);
		a.setSpeed20(null);
		a.setSpeed30(null);
		Vector<Armor> v = db.selectArmor(a);
		addItem("All");
		addItem("All Light");
		addItem("All Medium");
		addItem("All Heavy");
		for (int i = 0; i < v.size(); i++) {
			Armor f = (Armor) v.get(i);
			addItem(f.getName());
		}

	}
}
