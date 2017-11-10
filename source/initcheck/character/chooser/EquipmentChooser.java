package initcheck.character.chooser;

import initcheck.database.Equipment;
import initcheck.database.EquipmentDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class EquipmentChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public EquipmentChooser() {
		super();
		EquipmentDAO db = new EquipmentDAO();
		Vector<Equipment> v = db.getEquipment();
		for (int i = 0; i < v.size(); i++) {
			Equipment f = (Equipment) v.get(i);
			addItem(f);
		}

	}

	public void setSelectedEquipment(Equipment w) {
		for (int i = 0; i < getItemCount(); i++) {
			Equipment w2 = (Equipment) getItemAt(i);
			if (w2.equals(w)) {
				setSelectedIndex(i);
				break;
			}
		}

	}
}
