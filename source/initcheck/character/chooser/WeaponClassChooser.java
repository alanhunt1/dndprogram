package initcheck.character.chooser;

import initcheck.database.WeaponDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class WeaponClassChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public WeaponClassChooser() {
		super();

		WeaponDAO db = new WeaponDAO();
		Vector<String> v = db.getWeaponClasses();
		for (int i = 0; i < v.size(); i++) {
			String s = (String) v.get(i);
			addItem(s);
		}
		
		// add spell for type of weapon for determining high scores
		addItem("Spell");
	}
}
