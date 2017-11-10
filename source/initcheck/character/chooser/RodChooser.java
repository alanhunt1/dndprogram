package initcheck.character.chooser;

import initcheck.database.Armor;
import initcheck.database.Rods;
import initcheck.database.RodsDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class RodChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public RodChooser() {
		super();
		RodsDAO db = new RodsDAO();

		Vector<Rods> v = db.getRods();
		for (int i = 0; i < v.size(); i++) {
			Rods f = (Rods) v.get(i);
			addItem(f);
		}

	}

	public void setSelectedRod(Armor w) {
		for (int i = 0; i < getItemCount(); i++) {
			Rods w2 = (Rods) getItemAt(i);
			if (w2.getId().equals(w.getId())) {
				setSelectedIndex(i);
				break;
			}
		}

	}
}
