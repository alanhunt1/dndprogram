package initcheck.character.chooser;

import initcheck.database.Wonderous;
import initcheck.database.WonderousItemsDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class WonderousChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public WonderousChooser() {
		super();
		WonderousItemsDAO db = new WonderousItemsDAO();
		Vector<Wonderous> v = db.getWonderousItems();
		for (int i = 0; i < v.size(); i++) {
			Wonderous f = (Wonderous) v.get(i);
			addItem(f);
		}

	}
}
