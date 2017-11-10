package initcheck.character.chooser;

import initcheck.database.Ability;
import initcheck.database.ArmorAbilities;
import initcheck.database.ArmorAbilitiesDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class ArmorAbilityChooser extends JComboBox {
	private static final long serialVersionUID = 1L;

	public ArmorAbilityChooser() {
		super();
		addItem(new ArmorAbilities());
		ArmorAbilitiesDAO db = new ArmorAbilitiesDAO();
		Vector<ArmorAbilities> v = db.getArmorAbilities();
		for (int i = 0; i < v.size(); i++) {
			ArmorAbilities f = (ArmorAbilities) v.get(i);
			addItem(f);
		}
	}

	public ArmorAbilityChooser(String s) {
		super();
		addItem(new ArmorAbilities());
		ArmorAbilitiesDAO db = new ArmorAbilitiesDAO();
		Vector<ArmorAbilities> v = db.getArmorAbilitiesByType(s);
		for (int i = 0; i < v.size(); i++) {
			ArmorAbilities f = (ArmorAbilities) v.get(i);
			addItem(f);
		}
	}

	public void setSelectedAbility(Ability w) {
		if (w == null || w.getName() == null || w.getId() == null)
			return;
		for (int i = 0; i < getItemCount(); i++) {
			ArmorAbilities wa = (ArmorAbilities) getItemAt(i);
			if (w.getId().equals(wa.getId())) {
				setSelectedIndex(i);
				break;
			}
		}
	}
}
