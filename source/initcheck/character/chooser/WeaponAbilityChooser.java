package initcheck.character.chooser;

import initcheck.database.Ability;
import initcheck.database.WeaponAbilities;
import initcheck.database.WeaponAbilitiesDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class WeaponAbilityChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	String type = "melee";

	public WeaponAbilityChooser() {
		super();
		
	}

	public WeaponAbilityChooser(String s) {
		super();
		addItem(new WeaponAbilities());
		WeaponAbilitiesDAO db = new WeaponAbilitiesDAO();
		Vector<WeaponAbilities> v = db.getWeaponAbilitiesByType(s);
		for (int i = 0; i < v.size(); i++) {
			WeaponAbilities f = (WeaponAbilities) v.get(i);
			addItem(f);
		}
	}

	public void setChooserType(String s) {

		removeAllItems();
		addItem(new WeaponAbilities());
		WeaponAbilitiesDAO db = new WeaponAbilitiesDAO();
		Vector<WeaponAbilities> v = db.getWeaponAbilitiesByType(s);
		for (int i = 0; i < v.size(); i++) {
			WeaponAbilities f = (WeaponAbilities) v.get(i);
			addItem(f);
		}
	}

	public void setSelectedAbility(Ability w) {
		for (int i = 0; i < getItemCount(); i++) {
			WeaponAbilities wa = (WeaponAbilities) getItemAt(i);
			if (w.getName().equals(wa.getName())) {
				setSelectedIndex(i);
				break;
			}
		}
	}
}
