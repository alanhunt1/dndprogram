package initcheck.character.chooser;

import initcheck.database.Armor;
import initcheck.database.Staffs;
import initcheck.database.StaffsDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class StaffChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public StaffChooser() {
		super();
		StaffsDAO db = new StaffsDAO();

		Vector<Staffs> v = db.getStaffs();
		for (int i = 0; i < v.size(); i++) {
			Staffs f = (Staffs) v.get(i);
			addItem(f);
		}

	}

	public void setSelectedStaff(Armor w) {
		for (int i = 0; i < getItemCount(); i++) {
			Staffs w2 = (Staffs) getItemAt(i);
			if (w2.getId().equals(w.getId())) {
				setSelectedIndex(i);
				break;
			}
		}

	}
}
