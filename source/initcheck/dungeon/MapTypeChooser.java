package initcheck.dungeon;

import javax.swing.JComboBox;

public class MapTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public MapTypeChooser() {
		super();
		addItem("Dungeon");
		addItem("Terrain");
		addItem("Town");
		addItem("Building");
	}
}
