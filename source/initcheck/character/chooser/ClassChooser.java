package initcheck.character.chooser;

import initcheck.database.CharClass;
import initcheck.database.CharClassDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class ClassChooser extends JComboBox<CharClass> {

	private static final long serialVersionUID = 1L;

	public ClassChooser() {
		super();
		CharClassDAO db = new CharClassDAO();
		Vector<CharClass> v = db.getClasses();
		CharClass c = new CharClass();
		c.setCharClass("--Select--");
		addItem(c);
		for (int i = 0; i < v.size(); i++) {
			CharClass f = (CharClass) v.get(i);
			addItem(f);
		}

	}

	public void setSelectedClass(String c) {

		for (int i = 0; i < getItemCount(); i++) {
			CharClass c2 = (CharClass) getItemAt(i);
			if (c2.getId() != null && c2.getId().equals(c)) {
				setSelectedIndex(i);
				break;
			}
		}
	}
}
