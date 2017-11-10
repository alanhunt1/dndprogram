package initcheck.dungeon;

import javax.swing.JComboBox;

public class MapStyleChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public MapStyleChooser() {
		super();
		addItem("Square");
		addItem("Hex");
	}
}
