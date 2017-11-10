package initcheck.character.chooser;

import initcheck.database.InitDBC;

import java.util.Vector;

import javax.swing.JComboBox;

public class SizeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public SizeChooser() {
		super();
		InitDBC db = new InitDBC();
		Vector<String> v = db.getMonsterSizes();
		addItem("");
		for (int i = 0; i < v.size(); i++) {
			addItem((String) v.get(i));
		}

	}
}
