package initcheck.character.chooser;

import initcheck.database.InitDBC;

import java.util.Vector;

import javax.swing.JComboBox;

public class TerrainChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public TerrainChooser() {
		super();
		InitDBC db = new InitDBC();

		Vector<String> v = db.getTerrainTypes();
		addItem("Any");
		
		for (int i = 0; i < v.size(); i++) {
			String f = (String) v.get(i);
			addItem(f);
		}
	}

	
}
