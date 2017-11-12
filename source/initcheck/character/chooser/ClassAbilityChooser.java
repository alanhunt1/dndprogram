package initcheck.character.chooser;

import initcheck.database.ClassAbilities;
import initcheck.database.ClassAbilitiesDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class ClassAbilityChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	public ClassAbilityChooser() {
		super();
		addItem(new ClassAbilities());
		ClassAbilitiesDAO db = new ClassAbilitiesDAO();
		Vector<ClassAbilities> v = db.getClassAbilities();
		for (int i = 0; i < v.size(); i++) {
			ClassAbilities f = (ClassAbilities) v.get(i);
			addItem(f);
		}
	}

	public ClassAbilityChooser(String s) {
		super();
		addItem(new ClassAbilities());
		ClassAbilitiesDAO db = new ClassAbilitiesDAO();
		Vector<ClassAbilities> v = db.getClassAbilities(s);
		for (int i = 0; i < v.size(); i++) {
			ClassAbilities f = (ClassAbilities) v.get(i);
			addItem(f);
		}
	}

	public void setSelectedAbility(ClassAbilities w) {
		if (w == null || w.getName() == null || w.getId() == null)
			return;
		for (int i = 0; i < getItemCount(); i++) {
			ClassAbilities wa = (ClassAbilities) getItemAt(i);
			if (w.getId().equals(wa.getId())) {
				setSelectedIndex(i);
				break;
			}
		}
	}
}
