package initcheck.character.chooser;

import initcheck.database.Armor;
import initcheck.database.ArmorDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class ShieldProfChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public ShieldProfChooser() {
		super();
		ArmorDAO db = new ArmorDAO();
		Armor a = new Armor();
		a.setType("SHIELD");
		a.setBonus(null);
		a.setArmorbonus(null);

		Vector<Armor> v = db.selectArmor(a);
		addItem("All");

		for (int i = 0; i < v.size(); i++) {
			Armor f = (Armor) v.get(i);
			addItem(f.getName());
		}

	}
}
