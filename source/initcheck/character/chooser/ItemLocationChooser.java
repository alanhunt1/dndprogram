package initcheck.character.chooser;

import initcheck.database.ItemLocation;
import initcheck.database.ItemLocationDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class ItemLocationChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	public ItemLocationChooser() {
		super();
		ItemLocationDAO db = new ItemLocationDAO();
		Vector<ItemLocation> v = db.getItemLocations();
		for (int i = 0; i < v.size(); i++) {
			ItemLocation f = (ItemLocation) v.get(i);
			addItem(f);
		}

	}

	public void setSelectedLocation(String location) {
		for (int i = 0; i < getItemCount(); i++) {
			ItemLocation w2 = (ItemLocation) getItemAt(i);
			if (w2.getLocation().equals(location)) {
				setSelectedIndex(i);
				break;
			}
		}
	}
}
