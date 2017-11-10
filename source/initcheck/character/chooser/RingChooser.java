package initcheck.character.chooser;

import initcheck.database.Armor;
import initcheck.database.Rings;
import initcheck.database.RingsDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class RingChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public RingChooser() {
		super();
		RingsDAO db = new RingsDAO();

		Vector<Rings> v = db.getRings();
		for (int i = 0; i < v.size(); i++) {
			Rings f = (Rings) v.get(i);
			addItem(f);
		}

	}

	public void setSelectedRing(Armor w) {
		for (int i = 0; i < getItemCount(); i++) {
			Rings w2 = (Rings) getItemAt(i);
			if (w2.getId().equals(w.getId())) {
				setSelectedIndex(i);
				break;
			}
		}

	}
}
