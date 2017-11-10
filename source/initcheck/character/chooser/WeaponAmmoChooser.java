package initcheck.character.chooser;

import initcheck.database.Weapon;
import initcheck.database.WeaponDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class WeaponAmmoChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	private static Vector<Weapon> v;

	public WeaponAmmoChooser() {
		super();

		if (v == null) {
			WeaponDAO db = new WeaponDAO();
			v = db.selectWeapon(new Weapon());
		}
		for (int i = 0; i < v.size(); i++) {
			Weapon f = (Weapon) v.get(i);
			addItem(f);
		}

	}

	public void setSelectedWeapon(Weapon w) {
		for (int i = 0; i < getItemCount(); i++) {
			Weapon w2 = (Weapon) getItemAt(i);
			if (w2.getId().equals(w.getId())) {
				setSelectedIndex(i);
				break;
			}
		}

	}
}
