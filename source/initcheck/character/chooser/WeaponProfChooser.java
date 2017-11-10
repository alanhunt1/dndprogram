package initcheck.character.chooser;

import initcheck.database.Weapon;
import initcheck.database.WeaponDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class WeaponProfChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public WeaponProfChooser() {
		super();
		WeaponDAO db = new WeaponDAO();
		Vector<Weapon> v = db.getWeapons();
		addItem("All");
		addItem("All simple");
		addItem("All martial");
		addItem("All exotic");
		for (int i = 0; i < v.size(); i++) {
			Weapon f = (Weapon) v.get(i);
			addItem(f.getName());
		}

	}

}
