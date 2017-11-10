package initcheck.dungeon;

import javax.swing.JComboBox;

public class DrawFloodChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public DrawFloodChooser() {
		super();
		addItem("Wall");
		addItem("Corridor");
		addItem("Hole");
		addItem("Water");
		addItem("Land");
	}
}
